import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class ThreadWriteToTxt {

    public static void main(String[] args) {

        long startTime=System.currentTimeMillis();

        List<String> list=new ArrayList<String>();

        String url="http://localhost:8090/test/";

        for(int i=0;i<2000;i++){
            list.add(String.valueOf(i));
        }

        StringBuffer sb=new StringBuffer();
//
        ThreadWriteToTxt test=new ThreadWriteToTxt();
        test.handleList(list,3,sb,url);

//        for(String code:list){
//            HttpGet httpGet=new HttpGet(url+code);
//            CloseableHttpClient httpClient = HttpClients.createDefault();
//            try{
//                HttpResponse response=httpClient.execute(httpGet);
//                HttpEntity entity=response.getEntity();
//                String content = EntityUtils.toString(entity);
//                Msg msg = JSONObject.parseObject(content,Msg.class);
//                System.out.println(msg.getExtend().get("json"));
//                sb.append("Json:"+  msg.getExtend().get("json") +"\n");
//            }catch(Exception e){
//
//            }
//        }

    }

    public synchronized void handleList(List<String> data, int threadNum,StringBuffer sb,String url) {
        int length = data.size();
        int tl = length % threadNum == 0 ? length / threadNum : (length
                / threadNum + 1);

        for (int i = 0; i < threadNum; i++) {
            int end = (i + 1) * tl;
            SimulateGetJson thread = new SimulateGetJson(sb,  data, url, i * tl,end > length ? length : end);
            thread.start();
        }
    }


}
