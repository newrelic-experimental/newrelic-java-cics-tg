package com.newrelic.instrumentation.labs.cicstg;

import java.net.URI;

import com.ibm.ctg.client.ECIRequest;
import com.ibm.ctg.client.EPIRequest;
import com.ibm.ctg.client.ESIRequest;
import com.ibm.ctg.client.GatewayRequest;
import com.newrelic.api.agent.HttpParameters;
import com.newrelic.api.agent.NewRelic;

public class CicsTgUtils {

    private static final String LIBRARY = "CICS-TG";
    private static final String UNKNOWN = "UnknownCicsServer";

    private CicsTgUtils() {}

    public static void reportExternal(GatewayRequest request, String gatewayAddress, int gatewayPort) {
        try {
            String server = UNKNOWN;
            String program = "flow";

            if (request instanceof ECIRequest) {
                ECIRequest eci = (ECIRequest) request;
                if (eci.Server  != null && !eci.Server.trim().isEmpty())  server  = eci.Server.trim();
                if (eci.Program != null && !eci.Program.trim().isEmpty()) program = eci.Program.trim();
            } else if (request instanceof EPIRequest) {
                // Public field, same shape as ECIRequest.Server
                EPIRequest epi = (EPIRequest) request;
                if (epi.Server != null && !epi.Server.trim().isEmpty()) server = epi.Server.trim();
            } else if (request instanceof ESIRequest) {
                // Method-backed, not a public field — ESIRequest has no Program either.
                ESIRequest esi = (ESIRequest) request;
                String esiServer = esi.getServer();
                if (esiServer != null && !esiServer.trim().isEmpty()) server = esiServer.trim();
            }

            // Optional prefix so the node label can match the AppD name exactly
            // (newrelic.yml:  cics_tg: { host_prefix: "CTG-" }).
            String prefix = NewRelic.getAgent().getConfig().getValue("cics_tg.host_prefix", "");
            String host = sanitizeHost(prefix + server);

            StringBuilder uri = new StringBuilder("cics://").append(host);
            if (gatewayPort > 0) uri.append(':').append(gatewayPort);

            NewRelic.getAgent().getTracedMethod().reportAsExternal(
                HttpParameters.library(LIBRARY)
                    .uri(URI.create(uri.toString()))
                    .procedure(program)
                    .noInboundHeaders()
                    .build());

            // Gateway daemon identity, kept off the node label so the four CICS
            // servers behind one daemon do not collapse into a single node.
            if (gatewayAddress != null) {
                NewRelic.addCustomParameter("cics.gateway.address", gatewayAddress);
            }
            NewRelic.addCustomParameter("cics.server", server);
            NewRelic.addCustomParameter("cics.program", program);

        } catch (Throwable t) {
            // Instrumentation must never break the application.
            NewRelic.getAgent().getLogger().log(java.util.logging.Level.FINE,
                    t, "CICS-TG instrumentation failed to report external call");
        }
    }

    /** URI hosts reject characters that are legal in CICS APPLIDs ($, @, #). */
    private static String sanitizeHost(String raw) {
        String s = raw.replaceAll("[^A-Za-z0-9.-]", "-");
        return s.isEmpty() ? UNKNOWN : s;
    }
}
