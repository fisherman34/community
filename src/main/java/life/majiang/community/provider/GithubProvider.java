package life.majiang.community.provider;

import com.alibaba.fastjson.JSON;
import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import okhttp3.*;

import org.springframework.stereotype.Component;


import java.io.IOException;
import java.sql.SQLOutput;

/**
 * @author Sam
 * @create 2024-04-29 9:53 PM
 */
@Component
public class GithubProvider {
  public String getAccessToken(AccessTokenDTO accessTokenDTO){
    MediaType mediaType = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
    Request request = new Request.Builder()
              .url("https://github.com/login/oauth/access_token")
              .post(body)
              .build();
      try (Response response = client.newCall(request).execute()) {
        String string = response.body().string();
        String token = string.split("&")[0].split("=")[1];
        return token;
      } catch (Exception e) {
          e.printStackTrace();
      }
      return null;
  }

  public GithubUser getUser(String accessToken){
    String token = "Bearer " + accessToken;
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
            .url("https://api.github.com/user")
            .header("Authorization", token)
            .build();
    try {
      Response response = client.newCall(request).execute();
      String string = response.body().string();
      GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
      return githubUser;
    } catch (IOException e) {
    }
    return null;
  }
}
