package androidmapsdefinitivo.android.bajaintec.com.androidmapsdefinitivo.Control;

/**
 * Copyright(C) <2016> <BajaInTec>
 * This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **/

import android.os.AsyncTask;

/**
 * Created by fernando on 12/03/2016.
 */
public class WebServiceTask extends AsyncTask<String, Long, String> {

    private RestClient client;

    private String urls[] =
            {
                    "http://192.168.1.74:8080/BajaIntech/webresources/StreetSecurity/review_street",
                    "http://192.168.1.74:8080/BajaIntech/webresources/StreetSecurity/sign_in",
                    "http://192.168.1.74:8080/BajaIntech/webresources/StreetSecurity/sign_up",
                    "http://192.168.1.74:8080/BajaIntech/webresources/StreetSecurity/view_street_safety",
                    "http://192.168.1.74:8080/BajaIntech/webresources/StreetSecurity/review_services",
                    "http://192.168.1.74:8080/BajaIntech/webresources/StreetSecurity/view_street_safety_condition"
            };
    /*

    private String urls[] =
            {
                    "http://10.10.3.195:8080/BajaIntech/webresources/StreetSecurity/review_street",
                    "http://10.10.3.195:8080/BajaIntech/webresources/StreetSecurity/sign_in",
                    "http://10.10.3.195:8080/BajaIntech/webresources/StreetSecurity/sign_up",
                    "http://10.10.3.195:8080/BajaIntech/webresources/StreetSecurity/view_street_safety",
                    "http://10.10.3.195:8080/BajaIntech/webresources/StreetSecurity/review_services",
                    "http://10.10.3.195:8080/BajaIntech/webresources/StreetSecurity/view_street_safety_condition"
            };
    */
    @Override
    protected String doInBackground(String... params) {
        client = new RestClient(urls[Integer.parseInt(params[1])]);

        for (int x=2; x < params.length-1; x++){
            client.AddParam(params[x],params[x+1]);
        }


        try {
            if(params[0].equalsIgnoreCase("GET")) {
                client.Execute(RestClient.RequestMethod.GET);
            }else if(params[0].equalsIgnoreCase("POST")){
                client.Execute(RestClient.RequestMethod.POST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return client.getResponse();
    }


}