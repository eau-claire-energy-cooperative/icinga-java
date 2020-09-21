package com.ecec.rweber.icinga;

import java.io.IOException;


import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.ecec.rweber.icinga.action.IcingaAction;

public class IcingaApi {
	private Client m_http = null;
	private String m_url = null;
	
	public IcingaApi(String url,String username, String password) {
		m_url = url;
		
		//build an SSL context to get rid of cert warnings
		SSLContext sslcontext = null;
		try{
		 sslcontext = SSLContext.getInstance("TLS");

	    sslcontext.init(null, new TrustManager[]{new X509TrustManager() {
	        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
	        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
	        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
	    }}, new java.security.SecureRandom());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//setup the http client config
		ClientConfig clientConfig = new ClientConfig();
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username,password);
	    clientConfig.register(feature) ;
		
	    //build the client
		ClientBuilder b = ClientBuilder.newBuilder().withConfig(clientConfig).sslContext(sslcontext).hostnameVerifier(new HostnameVerifier(){

			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				//approve all host names on certs
				return true;
			}});
		
		m_http = b.build();
	}

	private JSONObject sendRequest(String endpoint, String postData, boolean header){
		WebTarget target = m_http.target(this.m_url + endpoint);
		
		JSONObject result = null;
		JSONParser parser = new JSONParser();

		try{
			//set GET override header, send json 
			Builder b = target.request().accept(MediaType.APPLICATION_JSON_TYPE);
			
			if(header)
			{
				b.header("X-HTTP-Method-Override", "GET");
			}
			
			Response response = b.post(Entity.entity(postData, MediaType.APPLICATION_JSON_TYPE));
			
			if (response.getStatus() != 200)
			{
				throw new IOException("Received non-OK response code for URI: " + target.getUri() + ": " + response.readEntity(String.class));
			}
			
			String resultString = response.readEntity(String.class);
			
			result = (JSONObject) parser.parse(resultString);
			
		}
		catch(ClassCastException e){
			e.printStackTrace();
			//don't do anything
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean action(IcingaAction a){
		boolean success = false;
		JSONObject qResult = this.sendRequest(a.getAction(), a.getPostData(), false);
		
		if(qResult.containsKey("results"))
		{
			JSONArray array = (JSONArray)qResult.get("results");
			
			if(((JSONObject)array.get(0)).get("code").toString().equals("200.0"))
			{
				success = true;
			}
		}

		return success;
	}
	
	public List<ResponseRow> search(IcingaQuery q){
		List<ResponseRow> result = new ArrayList<ResponseRow>();
		
		//convert the raw result to a list of objects
		JSONObject qResult = this.sendRequest(q.getEndpointTarget(), q.getPostData(), true);
		
		
		if(qResult.containsKey("results"))
		{
			JSONArray array = (JSONArray)qResult.get("results");
			ResponseRow aRow = null;
			JSONObject temp = null;
			
			for(int count =0; count < array.size(); count ++)
			{
				temp = (JSONObject)array.get(count);
				aRow = new ResponseRow(q);
				
				//add the attrs key
				JSONObject attrs = (JSONObject)temp.get("attrs");
				Iterator iter = attrs.keySet().iterator();
				String aKey = null;
				
				while(iter.hasNext())
				{
					aKey = iter.next().toString();
					
					aRow.put(aKey, attrs.get(aKey));
				}

				//any joined attributes are here
				if(temp.containsKey("joins"))
				{
					JSONObject joins = (JSONObject)temp.get("joins");
					Iterator objectKeys = ((JSONObject)temp.get("joins")).keySet().iterator();
					aKey = null;
					JSONObject keyObj = null;
					
					//go through all the join objects
					while(objectKeys.hasNext())
					{
						aKey = objectKeys.next().toString();
						keyObj = (JSONObject)joins.get(aKey);
						
						//get the attributes of this object and add them to the row
						Iterator attrIter = keyObj.keySet().iterator();
						String attrKey = null;
						
						while(attrIter.hasNext())
						{
							attrKey = attrIter.next().toString();
							
							//use the format objName.key for the key
							aRow.put(aKey + "." + attrKey, keyObj.get(attrKey));
						}
					}
				}
				
				result.add(aRow);
			}
			
			//need to sort after the fact since the api won't do it
			if(q.getOrder() != null)
			{
				Collections.sort(result, q.getOrder());
			}
		}
		
		return result;
	}
	
	public boolean hasResult(IcingaQuery q){
		boolean result = false;
		
		List<ResponseRow> response = this.search(q);
		
		if(response != null && response.size() > 0)
		{
			result = true;
		}
		
		return result;
	}
}
