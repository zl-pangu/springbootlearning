package com.zl.es.util;

import com.zl.common.utils.EsPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhouliang
 * @Date: 2018/7/4 15:44
 */
@Slf4j
@Component
public class ElasticsearchUtil {
    @Autowired
    private TransportClient transportClient;

    private static TransportClient client;

    /**
     * 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次，类似于Servlet的inti()方法。
     * 被@PostConstruct修饰的方法会在构造函数之后，init()方法之前运行。 @PreDestroy
     */
    @PostConstruct
    public void init() {
        client = this.transportClient;
    }

    /**
     * 创建索引
     * @param index
     * @return
     */
    public static boolean createIndex(String index){
        CreateIndexResponse indexResponse = client.admin().indices().prepareCreate(index).execute().actionGet();
        log.info("执行成功？{}",indexResponse.isAcknowledged());
        return indexResponse.isShardsAcknowledged();
    }

    /**
     * 删除索引
     * @param index
     * @return
     */
    public static boolean deleteIndex(String index){
        DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(index).execute().actionGet();
        if (dResponse.isAcknowledged()){
            log.info("delete index :"+index+",successfully!");
        }else{
            log.info("Fail to delete index:"+index);
        }
        return  dResponse.isAcknowledged();
    }

    /**
     * 判断索引是否存在
     * @param index
     * @return
     */
    public static boolean isIndexExist(String index){
        System.out.println(client);
        IndicesExistsResponse inExistsResponse = client.admin().indices().exists(new IndicesExistsRequest(index)).actionGet();
        if (inExistsResponse.isExists()){
            log.info("Index ["+index+"] is exist!");
        }else{
            log.info("Index ["+index+"] is not exist");
        }
        return inExistsResponse.isExists();
    }

    /**
     * 数据添加 自定义id
     * @param json
     * @param index
     * @param type
     * @param id
     * @return
     */
    public static String addData(String json, String index, String type, String id){
        //es 6.2.4传json字符串得加个XContentType.JSON；还可以传JsonObj,map.xml
        IndexResponse response = client.prepareIndex(index, type, id).setSource(json,XContentType.JSON).get();
        log.info("addData response status:{},id:{}",response.status().getStatus(),response.getId());
        return response.getId();
    }

    /**
     * 通过id删除数据
     * @param index
     * @param type
     * @param id
     */
    public static void deleteDataById(String index, String type, String id){
        DeleteResponse response = client.prepareDelete(index, type, id).execute().actionGet();
        log.info("deleteDataById response status:{},id:{}", response.status().getStatus(), response.getId());
    }

    /**
     * 通过id更新数据
     * @param json
     * @param index
     * @param type
     * @param id
     */
    public static void updateDataById(String json, String index, String type, String id){
        UpdateRequest request=new UpdateRequest();
        request.index(index).type(type).id(id).doc(json,XContentType.JSON);
        client.update(request);
    }

    /**
     * 根据id获取数据
     * @param index
     * @param type
     * @param id
     * @param fields
     * @return
     */
    public static Map<String, Object> searchDataById(String index, String type, String id, String fields) {
        GetRequestBuilder getRequestBuilder = client.prepareGet(index, type, id);
        if (StringUtils.isNotEmpty(fields)){
            getRequestBuilder.setFetchSource(fields.split(","),null);
        }
        GetResponse getResponse = getRequestBuilder.execute().actionGet();
        return getResponse.getSource();
    }

    /**
     * 使用分词查询并分页
     * @param index 索引名称
     * @param type  类型名称
     * @param startPage 当前页
     * @param pageSize  每页显示条数
     * @param query     查询条件
     * @param fields    需要显示的字段
     * @param sortField  拍戏字段
     * @param highlightField 高亮字段
     * @return
     */
    public static EsPage searchDataPage(String index, String type, int startPage, int pageSize, QueryBuilder query, String fields, String sortField, String highlightField) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index);
        if (StringUtils.isNotEmpty(type)){
            searchRequestBuilder.setTypes(type.split(","));
        }
        searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH);
        //需要显示的字段，逗号分隔（缺省为全部字段）
        if (StringUtils.isNotEmpty(fields)){
            searchRequestBuilder.setFetchSource(fields.split(","),null);
        }
        //排序字段
        if (StringUtils.isNotEmpty(sortField)){
            searchRequestBuilder.addSort(sortField,SortOrder.DESC);
        }
        //高亮字段
        if (StringUtils.isNotEmpty(highlightField)){
            HighlightBuilder highlightBuilder=new HighlightBuilder();
            //highlightBuilder.preTags("<span style='color:red' >");//设置前缀
            //highlightBuilder.postTags("</span>");//设置后缀
            //设置高亮字段
            highlightBuilder.field(highlightField);
            searchRequestBuilder.highlighter(highlightBuilder);
        }

        searchRequestBuilder.setQuery(query);
        //分页
        searchRequestBuilder.setFrom(startPage).setSize(pageSize);
        //是否按查询匹配度排序
        searchRequestBuilder.setExplain(true);
        //执行搜索
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
        long totalHits = searchResponse.getHits().getTotalHits();
        if (searchResponse.status().getStatus()==200){
            List<Map<String, Object>> sourceList  = setSearchResponse(searchResponse, highlightField);
            EsPage esPage = new EsPage(startPage, pageSize, (int) totalHits, sourceList);
            return esPage;
        }
        return null;
    }

    /**
     * 使用分词查询
     * @param index 索引字段
     * @param type  类型名称
     * @param query 查询条件
     * @param size  文件大小
     * @param fields 显示字段
     * @param sortField 排序字段
     * @param highlightField 高亮字段
     * @return
     */
    public static List<Map<String, Object>> searchListData(String index, String type, QueryBuilder query, Integer size, String fields, String sortField, String highlightField) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index);
        if (StringUtils.isNotEmpty(type)){
            searchRequestBuilder.setTypes(type.split(","));
        }
        if (StringUtils.isNotEmpty(highlightField)){
            HighlightBuilder highlightBuilder=new HighlightBuilder();
            highlightBuilder.field(highlightField);
            searchRequestBuilder.highlighter(highlightBuilder);
        }
        searchRequestBuilder.setQuery(query);
        if (StringUtils.isNotEmpty(fields)){
            searchRequestBuilder.setFetchSource(fields.split(","),null);
        }
        searchRequestBuilder.setFetchSource(true);
        if (StringUtils.isNotEmpty(sortField)){
            searchRequestBuilder.addSort(sortField,SortOrder.DESC);
        }
        if (size != null && size > 0) {
            searchRequestBuilder.setSize(size);
        }
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

        long totalHits = searchResponse.getHits().totalHits;
        long length = searchResponse.getHits().getHits().length;

        log.info("共查询到[{}]条数据,处理数据条数[{}]", totalHits, length);

        if (searchResponse.status().getStatus() == 200) {
            return setSearchResponse(searchResponse, highlightField);
        }
            return null;
    }

    /**
     * 高亮结果集的特殊处理
     * @param searchResponse
     * @param highlightField
     * @return
     */
    private static List<Map<String, Object>> setSearchResponse(SearchResponse searchResponse, String highlightField) {
        List<Map<String,Object>> sourceList=new ArrayList<>();
        StringBuffer stringBuffer=new StringBuffer();
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit searchHit: hits) {
            searchHit.getSourceAsMap().put("id",searchHit.getId());
            if (StringUtils.isNotEmpty(highlightField)){
                Text[] fragments = searchHit.getHighlightFields().get(highlightField).getFragments();
                if (fragments!=null){
                    for (Text str: fragments) {
                        stringBuffer.append(str.string());
                    }
                }
                //遍历高亮结果集，覆盖正常的结果集
                searchHit.getSourceAsMap().put(highlightField,stringBuffer.toString());
            }
            sourceList.add(searchHit.getSourceAsMap());
        }
        return sourceList;
    }
}
