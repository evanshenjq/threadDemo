import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.List;

public class SimulateGetJson extends Thread {

    public StringBuffer sb;
    public List<String> codes;
    public String url;
    private int start;
    private int end;

    SimulateGetJson(StringBuffer sb,List<String> codes,String url,int start,int end){
        this.sb=sb;
        this.codes=codes;
        this.url=url;
        this.start=start;
        this.end=end;
    }

    public void run() {
        getJsonString();
    }

    public synchronized void getJsonString(){
        List<String> list=codes.subList(start,end);
        for(String code:list){
            HttpGet httpGet=new HttpGet(url+code);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            try{
                HttpResponse response=httpClient.execute(httpGet);
                HttpEntity entity=response.getEntity();
                String content = EntityUtils.toString(entity);
                Msg msg = JSONObject.parseObject(content,Msg.class);
                System.out.println(msg.getExtend().get("json"));
                sb.append("Json:"+  msg.getExtend().get("json") +"\n");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
