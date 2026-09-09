package com.ibm.ctg.client;

import java.io.IOException;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.cicstg.CicsTgUtils;

@Weave(type = MatchType.ExactClass, originalName = "com.ibm.ctg.client.JavaGateway")
public class JavaGateway_Instrumentation {

    // Field matching: these resolve to the original class's existing private fields.
    private String strAddress = Weaver.callOriginal();
    private int iPort = Weaver.callOriginal();

    @Trace
    public int flow(GatewayRequest gatRequest) throws IOException {
        CicsTgUtils.reportExternal(gatRequest, strAddress, iPort);
        return Weaver.callOriginal();
    }
}
