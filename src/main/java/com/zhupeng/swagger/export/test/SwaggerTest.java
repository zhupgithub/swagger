package com.zhupeng.swagger.export.test;

import com.zhupeng.swagger.export.utils.StreamTool;
import io.github.robwin.markup.builder.MarkupLanguage;
import io.github.robwin.swagger2markup.GroupBy;
import io.github.robwin.swagger2markup.Swagger2MarkupConverter;
import io.swagger.io.HttpClient;

import javax.annotation.PostConstruct;

import java.io.InputStream;

import static com.zhupeng.swagger.export.constants.Constant.*;

/**
 * @author zhuepng
 */
public class SwaggerTest {
    /**
     * 服务器地址
     */
    private static String SERVICE_URL = "http://127.0.0.1:9001";

    static {
        StreamTool.checkFile(OUTPUT_DIR);
    }


    @PostConstruct
    public void int2(){
        try {
            System.out.println("开始中……");
            outputJson();
            // 这个outputDir必须和插件里面<generated></generated>标签配置一致
            Swagger2MarkupConverter.from(FILE_PATH)
                    .withPathsGroupedBy(GroupBy.TAGS)// 按tag排序
                    .withMarkupLanguage(MarkupLanguage.ASCIIDOC)// 格式
                    .withExamples(SNIPPET_DIR)
                    .build()
                    .intoFolder(OUTPUT_DIR);// 输出
            System.out.println("结束了！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void outputJson() throws Exception {
        String url = SERVICE_URL + URI;
        HttpClient httpClient = new HttpClient(url);
        System.out.println("开始请求:" + url);
        InputStream ipputStream = httpClient.execute();
        byte[] data = StreamTool.read(ipputStream);
        System.out.println("请求结果:" + new String(data, "UTF-8"));
        StreamTool.saveDataToFile(data, FILE_PATH);
        System.out.println("请求结果已成功保存到本地:" + FILE_PATH);

    }
}
