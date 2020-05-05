package com.helger.smpclient.url;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainTestCNAME2NAPTR
{

  private static final Logger LOGGER = LoggerFactory.getLogger (MainTestCNAME2NAPTR.class);

  public static void main (final String [] args) throws Exception
  {
    String s = NAPTRResolver.resolveFromNAPTR ("AM7VC3VF63WABJQGQRHI36U2K6N6EJR7SHL6ELGLR7YRYQSFWOVQ.iso6523-actorid-upis.0151.test.participant.bdxl.services.",
                                               null,
                                               NAPTRResolver.DNS_UNAPTR_SERVICE_NAME_META_SMP);
    LOGGER.info ("='" + s + "'");

    s = NAPTRResolver.resolveFromNAPTR ("CDEF123456789012345678901234567890123456789012345678.iso6523-actorid-upis.0151.test.participant.bdxl.services.",
                                        null,
                                        NAPTRResolver.DNS_UNAPTR_SERVICE_NAME_META_SMP);
    LOGGER.info ("='" + s + "'");
  }
}
