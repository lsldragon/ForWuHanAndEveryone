package lsl.wuhanJY;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class GetData {

	private static String results1;
	private static String results2;
	private static String result;
	private static String overallData;

	public static String getAllData() {

		String overallDataString = getOverData();
		String heNanDataString = getData();

		String emailContents = overallDataString + "\n\n" + heNanDataString;
		return emailContents;

	}

	private static String getOverData() {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;

		HttpGet request = new HttpGet("https://lab.isaaclin.cn/nCoV/api/overall?latest=0");
		request.setHeader("User-Agent",
				"Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Mobile Safari/537.36");

		try {
			response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				HttpEntity httpEntity = response.getEntity();
				String htmlString = EntityUtils.toString(httpEntity, "utf-8");

				JSONObject jsonObject = JSONObject.parseObject(htmlString);
				String string = jsonObject.getString("results");

				JSONArray jsonArray = JSONArray.parseArray(string);
				String string2 = jsonArray.getString(0);

				JSONObject dataObject = JSONObject.parseObject(string2);

				String confirmedCount = dataObject.getString("confirmedCount");
				String curedCount = dataObject.getString("curedCount");
				String deadCount = dataObject.getString("deadCount");
				String suspectedCount = dataObject.getString("suspectedCount");

				overallData = "全国:" + "\n" + "确诊: " + confirmedCount + "\n" + "治愈: " + curedCount + "\n" + "死亡: "
						+ deadCount + "\n" + "疑似: " + suspectedCount;

			} else {
				System.out.println("ERROR");
				System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
			}
		} catch (Exception e) {
		} finally {
			HttpClientUtils.closeQuietly(response);
			HttpClientUtils.closeQuietly(httpClient);
		}

		return overallData;
	}

	private static String getData() {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;

		HttpGet request = new HttpGet("https://lab.isaaclin.cn/nCoV/api/area?latest=0&province=河南省");
		request.setHeader("User-Agent",
				"Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Mobile Safari/537.36");

		try {
			response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				HttpEntity httpEntity = response.getEntity();
				String htmlString = EntityUtils.toString(httpEntity, "utf-8");
				JSONObject jsonObject = JSONObject.parseObject(htmlString);
				String string = jsonObject.getString("results");

				JSONArray jsonArray = JSONArray.parseArray(string);
				JSONObject jsonObject2 = jsonArray.getJSONObject(0);
				String confirmedCount = jsonObject2.getString("confirmedCount");
				String curedCount = jsonObject2.getString("curedCount");
				String deadCount = jsonObject2.getString("deadCount");
				String suspectedCount = jsonObject2.getString("suspectedCount");
				results1 = "河南省：\n" + "确诊：" + confirmedCount + "\n" + "治愈：" + curedCount + "\n" + "死亡：" + deadCount
						+ "\n" + "疑似：" + suspectedCount;

				String cities = jsonObject2.getString("cities");
				JSONArray jsonArray2 = JSONArray.parseArray(cities);

				StringBuilder sb = new StringBuilder();

				for (int i = 0; i < 18; i++) {

					JSONObject jsonObject3 = jsonArray2.getJSONObject(i);

					String confirmedCount2 = jsonObject3.getString("confirmedCount");
					String curedCount2 = jsonObject3.getString("curedCount");
					String cityName2 = jsonObject3.getString("cityName");
					String deadCount2 = jsonObject3.getString("deadCount");
					String suspectedCount2 = jsonObject3.getString("suspectedCount");

					results2 = "城市名: " + cityName2 + "\n" + "确诊: " + confirmedCount2 + "\n" + "治愈: " + curedCount2
							+ "\n" + "死亡: " + deadCount2 + "\n" + "疑似: " + suspectedCount2 + "\n";
					sb.append(results2 + "\n");
				}

				result = results1 + "\n\n" + "====武汉加油分割线====" + "\n" + sb.toString();

			} else {
				System.out.println("ERROR");
				System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
			}
		} catch (Exception e) {
		} finally {
			HttpClientUtils.closeQuietly(response);
			HttpClientUtils.closeQuietly(httpClient);
		}

		return result;
	}
}
