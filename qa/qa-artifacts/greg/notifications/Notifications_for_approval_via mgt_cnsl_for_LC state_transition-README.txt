Life Cycle - Notifications for Approval
=========================================

1. Add the following life cycle.
2. Subscribe for LC approval Needed.
3. Create a new service and attach this LC.
4. Then promote it and refresh.
5. You will be able to see in the mgt console a notification and an extra field for approval via admin.
6. Without clickking it, Promote button will not be available.

When promoting, approval by an admin will be needed.



<aspect name="ServiceLifeCycle1" class="org.wso2.carbon.governance.registry.extensions.aspects.DefaultLifeCycle">
    <configuration type="literal">
        <lifecycle>
            <scxml xmlns="http://www.w3.org/2005/07/scxml"
                   version="1.0"
                   initialstate="Development">
                <state id="Development">
                    <datamodel>
                        <data name="checkItems">
                            <item name="Code Completed" forEvent="">
                                <!--<permissions>
                                    <permission roles=""/>
                                </permissions>
                                <validations>
                                    <validation forEvent="" class="">
                                        <parameter name="" value=""/>
                                    </validation>
                                </validations>-->
                            </item>
                            <item name="WSDL, Schema Created" forEvent="">
                            </item>
                            <item name="QoS Created" forEvent="">
                            </item>
                        </data>
			<data name="transitionExecution">
                            <execution forEvent="Promote" class="org.wso2.carbon.governance.registry.extensions.executors.ServiceVersionExecutor">
                                <parameter name="currentEnvironment" value="/_system/governance/trunk/{@resourcePath}/{@resourceName}"/>
                                <parameter name="targetEnvironment" value="/_system/governance/branches/testing/{@resourcePath}/{@version}/{@resourceName}"/>
                                <parameter name="service.mediatype" value="application/vnd.wso2-service+xml"/>
                                <parameter name="wsdl.mediatype" value="application/wsdl+xml"/>
                                <parameter name="endpoint.mediatype" value="application/vnd.wso2.endpoint"/>
                            </execution>			
                        </data>
			<data name="transitionUI">
                            <ui forEvent="Promote" href="../lifecycles/pre_invoke_aspect_ajaxprocessor.jsp?currentEnvironment=/_system/governance/trunk/"/>
                        </data>
                        <!--<data name="transitionValidation">
                            <validation forEvent="" class="">
                                <parameter name="" value=""/>
                            </validation>
                        </data>
                        <data name="transitionPermission">
                            <permission forEvent="" roles=""/>
                        </data>
                        <data name="transitionScripts">
                            <js forEvent="">
                                <console function="">
                                    <script type="text/javascript">
                                    </script>
                                </console>
                                <server function="">
                                    <script type="text/javascript"></script>
                                </server>
                            </js>
                        </data>-->
                        <data name="transitionApproval">
                            <approval forEvent="Promote" roles="" votes="1"/>
                        </data>
                        <data name="transitionScripts">
                            <js forEvent="Promote">
                                <console function="showServiceList">
<script type="text/javascript">
                                        showServiceList = function() { var element = document.getElementById('hidden_media_type'); var mediaType = ""; if (element) { mediaType = element.value;} if (mediaType == "application/vnd.wso2-service+xml") { location.href = unescape("../generic/list.jsp?region=region3%26item=governance_list_service_menu%26key=service%26breadcrumb=Services%26singularLabel=Service%26pluralLabel=Services"); } }
</script>
                                </console>
                            </js>
                        </data>
                    </datamodel>
                    <transition event="Promote" target="Testing"/>                  
                </state>
                <state id="Testing">
                    <datamodel>
                        <data name="checkItems">
                            <item name="Effective Inspection Completed" forEvent="">
                            </item>
                            <item name="Test Cases Passed" forEvent="">
                            </item>
                            <item name="Smoke Test Passed" forEvent="">
                            </item>
                        </data>
                        <data name="transitionExecution">
                            <execution forEvent="Promote" class="org.wso2.carbon.governance.registry.extensions.executors.ServiceVersionExecutor">
                                <parameter name="currentEnvironment" value="/_system/governance/branches/testing/{@resourcePath}/{@version}/{@resourceName}"/>
                                <parameter name="targetEnvironment" value="/_system/governance/branches/production/{@resourcePath}/{@version}/{@resourceName}"/>
                                <parameter name="service.mediatype" value="application/vnd.wso2-service+xml"/>
                                <parameter name="wsdl.mediatype" value="application/wsdl+xml"/>
                                <parameter name="endpoint.mediatype" value="application/vnd.wso2.endpoint"/>
                            </execution>
			    <execution forEvent="Demote" class="org.wso2.carbon.governance.registry.extensions.executors.DemoteActionExecutor">
                            </execution>
                        </data>
			<data name="transitionUI">
                            <ui forEvent="Promote" href="../lifecycles/pre_invoke_aspect_ajaxprocessor.jsp?currentEnvironment=/_system/governance/branches/testing/"/>
                        </data>
                        <data name="transitionScripts">
                            <js forEvent="Promote">
                                <console function="showServiceList">
<script type="text/javascript">
                                        showServiceList = function() { var element = document.getElementById('hidden_media_type'); var mediaType = ""; if (element) { mediaType = element.value;} if (mediaType == "application/vnd.wso2-service+xml") { location.href = unescape("../generic/list.jsp?region=region3%26item=governance_list_service_menu%26key=service%26breadcrumb=Services%26singularLabel=Service%26pluralLabel=Services"); } }
</script>
                                </console>
                            </js>
                        </data>
                    </datamodel>
                    <transition event="Promote" target="Production"/>
                    <transition event="Demote" target="Development"/>
                </state>
                <state id="Production">
                    <datamodel>
                        <data name="transitionExecution">
                            <execution forEvent="Demote" class="org.wso2.carbon.governance.registry.extensions.executors.DemoteActionExecutor">
                            </execution>
                            <execution forEvent="Publish" class="org.wso2.carbon.governance.registry.extensions.executors.apistore.ApiStoreExecutor">
                            </execution>
                        </data>
                    </datamodel>
                    <transition event="Publish" target="Published.to.APIStore"/>
                    <transition event="Demote" target="Testing"/>
                </state>
                <state id="Published.to.APIStore">
                </state>                
            </scxml>
        </lifecycle>
    </configuration>
</aspect>








