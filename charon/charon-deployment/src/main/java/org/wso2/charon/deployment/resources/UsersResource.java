/*
*  Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.charon.deployment.resources;

import org.wso2.charon.core.exceptions.CharonException;
import org.wso2.charon.core.extensions.UserManager;
import org.wso2.charon.core.protocol.SCIMResponse;
import org.wso2.charon.core.protocol.endpoints.UserResourceEndpoint;
import org.wso2.charon.core.schema.SCIMConstants;
import org.wso2.charon.utils.DefaultCharonManager;
import org.wso2.charon.utils.builders.JAXRSResponseBuilder;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;

/**
 * JAX-RS Service that exposes the Users Resource in SCIM Service Provider/
 */
@Path("/Users")
public class UsersResource {

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam(SCIMConstants.CommonSchemaConstants.ID) String id,
                            @HeaderParam("userName") String userName,
                            @HeaderParam("password") String password,
                            @HeaderParam("Accept") String format,
                            @HeaderParam("Authorization") String authorization) {

        try {
            //authenticate the request
            DefaultCharonManager defaultCharonManager = DefaultCharonManager.getInstance();
            defaultCharonManager.handleAuthentication(new HashMap<String,String>());
            // defaults to application/json.
            if (format == null) {
                format = SCIMConstants.JSON;
            }

            //obtain the user store manager according to the relevant tenant.
            UserManager userManager = DefaultCharonManager.getInstance().getUserManager(
                    userName);

            //create charon-SCIM user endpoint and hand-over the request.
            UserResourceEndpoint userResourceEndpoint = new UserResourceEndpoint();

            SCIMResponse scimResponse = userResourceEndpoint.get(id, format, userManager);
            //needs to check the code of the response and return 200 0k or other error codes
            // appropriately.
            return new JAXRSResponseBuilder().buildResponse(scimResponse);

        } catch (CharonException e) {
            //create SCIM response with code as the same of exception and message as error message of the exception
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.\

        }
        return null;
    }

    @POST
    public Response createUser(@HeaderParam("Content-Type") String inputFormat,
                               @HeaderParam("Accept") String outputFormat,
                               @HeaderParam("userName") String userName,
                               @HeaderParam("password") String password,
                               @HeaderParam("Authorization") String authorization,
                               String resourceString) {
        /*try {
            //authenticate the request
            DefaultCharonManager.handleAuthentication(userName, password, authorization);

            //set the format in which the response should be encoded, if not specified in the request,
            // defaults to application/json.
            if (outputFormat == null) {
                outputFormat = SCIMConstants.APPLICATION_JSON;
            }

            //obtain the user store manager according to the relevant tenant.
            UserManager userManager = DefaultCharonManager.getUserManager(
                    userName);

            //create charon-SCIM user endpoint and hand-over the request.
            UserResourceEndpoint userResourceEndpoint = new UserResourceEndpoint();

            SCIMResponse response = (userResourceEndpoint.create(resourceString, inputFormat, outputFormat, userManager));

            return new JAXRSResponseBuilder().buildResponse(response);

        } catch (InternalServerException e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }*/
        return null;
    }
}
