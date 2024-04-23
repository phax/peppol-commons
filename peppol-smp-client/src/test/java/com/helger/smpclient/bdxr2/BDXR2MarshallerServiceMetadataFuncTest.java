/*
 * Copyright (C) 2015-2024 Philip Helger
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.smpclient.bdxr2;

import static org.junit.Assert.assertNull;

import java.nio.charset.StandardCharsets;

import org.junit.Test;

import com.helger.jaxb.validation.DoNothingValidationEventHandler;
import com.helger.smpclient.bdxr2.marshal.BDXR2MarshallerServiceMetadata;
import com.helger.xsds.bdxr.smp2.ServiceMetadataType;

public final class BDXR2MarshallerServiceMetadataFuncTest
{
  @Test
  public void testDbnaAvalara ()
  {
    final String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ServiceMetadata xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://docs.oasis-open.org/bdxr/ns/SMP/2/ServiceMetadata\" xmlns:ext=\"http://docs.oasis-open.org/bdxr/ns/SMP/2/ExtensionComponents\" xmlns:sma=\"http://docs.oasis-open.org/bdxr/ns/SMP/2/AggregateComponents\" xmlns:smb=\"http://docs.oasis-open.org/bdxr/ns/SMP/2/BasicComponents\"><smb:SMPVersionID>2.0</smb:SMPVersionID><smb:ID schemeID=\"bdx-docid-qns\" schemeName=\"QName/SubtypeIdentifier\">bdx-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##DBNAlliance-1.0-data-Core</smb:ID><smb:ParticipantID schemeID=\"DUNS\">079961550</smb:ParticipantID><sma:ProcessMetadata><sma:Endpoint><smb:TransportProfileID>bdxr-as4-1.0#dbnalliance-1.0</smb:TransportProfileID><smb:Description>AS4 access point</smb:Description><smb:Contact>as4-ap@example.com</smb:Contact><smb:AddressURI>https://einvoicing.qa.avalara.io/as4/dbna</smb:AddressURI><sma:Certificate><smb:TypeCode>bdxr-as4-signing-encryption</smb:TypeCode><smb:Description>Access Point certificate for both signing and encryption</smb:Description>" +
                     "<smb:ActivationDate>01/09/2024 16:09:15</smb:ActivationDate>" +
                     "<smb:ExpirationDate>01/08/2025 16:09:15</smb:ExpirationDate>" +
                     "<smb:ContentBinaryObject mimeCode=\"application/base64\">MIIFQjCCBCqgAwIBAgIUbpPdQs98NpRJHtJDYJ1iw3JN1JQwDQYJKoZIhvcNAQELBQAwgbkxCzAJBgNVBAYTAlVTMQ4wDAYDVQQIEwVUZXhhczEQMA4GA1UEBxMHSG91c3RvbjEOMAwGA1UEERMFNzcwNTYxHjAcBgNVBAkTFTMgUml2ZXIgV2F5IFN1aXRlIDkyMDEqMCgGA1UEChMhRGlnaXRhbCBCdXNpbmVzcyBOZXR3b3JrIEFsbGlhbmNlMSwwKgYDVQQDEyNEQk5BbGxpYW5jZSBEZW1vIEludGVybWVkaWF0ZSBQaWxvdDAeFw0yNDAxMDkxNjA5MTVaFw0yNTAxMDgxNjA5MTVaMIGkMSgwJgYJKoZIhvcNAQkBFhlhbmRyZWEubW9yb25pQGF2YWxhcmEuY29tMRAwDgYDVQQDDAdBVkFMQVJBMRgwFgYDVQRhEw9EVU5TOjoxODU5NjIxNjkxFDASBgNVBAsMC0F2YWxhcmEgU01QMRQwEgYDVQQKDAtBdmFsYXJhIEluYzETMBEGA1UECAwKV2FzaGluZ3RvbjELMAkGA1UEBhMCVVMwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCwLFWgyP38Ulpr0GRjjzp+RW78lqYo9OYDvGX5WVhzGoaoFrDht52l32cXjka9Ck+266I1c3Z3jNyrAh+DDF0MFAPBCTgTc6NXkla5yvZmZx3IKsbVaT3n79Qj1gydBgsjl0m+CQfUjbsF5mD2MjODDldm2xZbtUPIqjalrQlDo0CcerShKCyfJqO/wrRyzoMCnAAe7mg8hTYw57kpfgQ09CmQH/SKO6IYsGv4Hel6BLRS2sjAGASXLrTQ3f+WKQbpputoorhD3spHOpCSzGFJ3ihKezlMf3aSIZkqqd1PAgmu6NS/dmGTLD3sjQOegW3sTBj6N9OFXy34pE299l7ZAgMBAAGjggFTMIIBTzAMBgNVHRMBAf8EAjAAMB0GA1UdDgQWBBQXHMVjmuktZgqu+ZqFRh1wBqlJMjAfBgNVHSMEGDAWgBT/PuDIo+wQVT8iyAq4nPbufDYiyDAOBgNVHQ8BAf8EBAMCBaAwgZYGCCsGAQUFBwEBBIGJMIGGMC0GCCsGAQUFBzABhiFodHRwOi8vb2NzcC5kZW1vLm9uZS5kaWdpY2VydC5jb20wVQYIKwYBBQUHMAKGSWh0dHA6Ly9jYWNlcnRzLmRlbW8ub25lLmRpZ2ljZXJ0LmNvbS9EQk5BbGxpYW5jZURlbW9JbnRlcm1lZGlhdGVQaWxvdC5jcnQwVgYDVR0fBE8wTTBLoEmgR4ZFaHR0cDovL2NybC5kZW1vLm9uZS5kaWdpY2VydC5jb20vREJOQWxsaWFuY2VEZW1vSW50ZXJtZWRpYXRlUGlsb3QuY3JsMA0GCSqGSIb3DQEBCwUAA4IBAQCWdZ+A+drOduOY7Nabhz1HM5qCDVPLKUy3VNcAMoM34UdIx0wfmVcormpJOkcdBPhgvOIAXqarZK0hHfcdXEgieSVSJXlDKAPFJ7rPmrVZC4i2oH9wz0qgqmvlJ7ibwOp3mTpU//Bo3Bzkms/divuIle96KuSXshaRKXZcB3inwT31lW7mFbhlVMOvGozWcxUFQmCAU2Zds7CDeVJziHDjZSt/Nd/Bew/tCwaOT2R5jarRrHFy+gTEfSIOa2tbX2Vkf248tBkUkrcNkVAQBBNzqCe2o0xVUmbLaKqNH9ZTsHltdnE5U54EvVYHQVU/2rSuh35VR8wKpt9WLVN064Oi</smb:ContentBinaryObject></sma:Certificate></sma:Endpoint></sma:ProcessMetadata>" +
                     "<Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\"><SignedInfo><CanonicalizationMethod Algorithm=\"http://www.w3.org/2006/12/xml-c14n11\" /><SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256\" /><Reference URI=\"\"><Transforms><Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\" /></Transforms><DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\" /><DigestValue>IpceXZ27T73kkEHfLuoA88pp1iLXYeR8bvvsrkEtC7M=</DigestValue></Reference></SignedInfo><SignatureValue>cqyb3GNkRkj/xloXzRiv5OzGpCRcr1Tc8FZMSaNacRGy6rd2u+I5F4hGU31MMJ3iHZrE57XbKORXfJ9zrnm4qPPGS2MoWFMBpgs9LNHGbI7rle/Mz+gl49I+DrSEXJLt1tVo9vuzAQMd3aLFQQn5niXonqajlRqaCJUG1VxCqfbNDc/0eqRP64VMK5OK5Oyjo6cpwmpHsFa037StMOHlFsrs0osO/FXYvytdCDVNttsBUPopU33qV6MpGoGFpMMwEj8yhTRP22accV8H0nRV0VVBCaT1NtIXl7RHz6KZbkiTK40DY18n/qbqWK27IfUBEjRbNW9Fm9tfmoEW2vmCjg==</SignatureValue><KeyInfo><X509Data><X509SubjectName>C=US, S=Washington, O=Avalara Inc, OU=Avalara SMP, organizationIdentifier=DUNS::185962169, CN=AVALARA, E=andrea.moroni@avalara.com</X509SubjectName><X509Certificate>MIIFQjCCBCqgAwIBAgIUbpPdQs98NpRJHtJDYJ1iw3JN1JQwDQYJKoZIhvcNAQELBQAwgbkxCzAJBgNVBAYTAlVTMQ4wDAYDVQQIEwVUZXhhczEQMA4GA1UEBxMHSG91c3RvbjEOMAwGA1UEERMFNzcwNTYxHjAcBgNVBAkTFTMgUml2ZXIgV2F5IFN1aXRlIDkyMDEqMCgGA1UEChMhRGlnaXRhbCBCdXNpbmVzcyBOZXR3b3JrIEFsbGlhbmNlMSwwKgYDVQQDEyNEQk5BbGxpYW5jZSBEZW1vIEludGVybWVkaWF0ZSBQaWxvdDAeFw0yNDAxMDkxNjA5MTVaFw0yNTAxMDgxNjA5MTVaMIGkMSgwJgYJKoZIhvcNAQkBFhlhbmRyZWEubW9yb25pQGF2YWxhcmEuY29tMRAwDgYDVQQDDAdBVkFMQVJBMRgwFgYDVQRhEw9EVU5TOjoxODU5NjIxNjkxFDASBgNVBAsMC0F2YWxhcmEgU01QMRQwEgYDVQQKDAtBdmFsYXJhIEluYzETMBEGA1UECAwKV2FzaGluZ3RvbjELMAkGA1UEBhMCVVMwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCwLFWgyP38Ulpr0GRjjzp+RW78lqYo9OYDvGX5WVhzGoaoFrDht52l32cXjka9Ck+266I1c3Z3jNyrAh+DDF0MFAPBCTgTc6NXkla5yvZmZx3IKsbVaT3n79Qj1gydBgsjl0m+CQfUjbsF5mD2MjODDldm2xZbtUPIqjalrQlDo0CcerShKCyfJqO/wrRyzoMCnAAe7mg8hTYw57kpfgQ09CmQH/SKO6IYsGv4Hel6BLRS2sjAGASXLrTQ3f+WKQbpputoorhD3spHOpCSzGFJ3ihKezlMf3aSIZkqqd1PAgmu6NS/dmGTLD3sjQOegW3sTBj6N9OFXy34pE299l7ZAgMBAAGjggFTMIIBTzAMBgNVHRMBAf8EAjAAMB0GA1UdDgQWBBQXHMVjmuktZgqu+ZqFRh1wBqlJMjAfBgNVHSMEGDAWgBT/PuDIo+wQVT8iyAq4nPbufDYiyDAOBgNVHQ8BAf8EBAMCBaAwgZYGCCsGAQUFBwEBBIGJMIGGMC0GCCsGAQUFBzABhiFodHRwOi8vb2NzcC5kZW1vLm9uZS5kaWdpY2VydC5jb20wVQYIKwYBBQUHMAKGSWh0dHA6Ly9jYWNlcnRzLmRlbW8ub25lLmRpZ2ljZXJ0LmNvbS9EQk5BbGxpYW5jZURlbW9JbnRlcm1lZGlhdGVQaWxvdC5jcnQwVgYDVR0fBE8wTTBLoEmgR4ZFaHR0cDovL2NybC5kZW1vLm9uZS5kaWdpY2VydC5jb20vREJOQWxsaWFuY2VEZW1vSW50ZXJtZWRpYXRlUGlsb3QuY3JsMA0GCSqGSIb3DQEBCwUAA4IBAQCWdZ+A+drOduOY7Nabhz1HM5qCDVPLKUy3VNcAMoM34UdIx0wfmVcormpJOkcdBPhgvOIAXqarZK0hHfcdXEgieSVSJXlDKAPFJ7rPmrVZC4i2oH9wz0qgqmvlJ7ibwOp3mTpU//Bo3Bzkms/divuIle96KuSXshaRKXZcB3inwT31lW7mFbhlVMOvGozWcxUFQmCAU2Zds7CDeVJziHDjZSt/Nd/Bew/tCwaOT2R5jarRrHFy+gTEfSIOa2tbX2Vkf248tBkUkrcNkVAQBBNzqCe2o0xVUmbLaKqNH9ZTsHltdnE5U54EvVYHQVU/2rSuh35VR8wKpt9WLVN064Oi</X509Certificate></X509Data></KeyInfo></Signature></ServiceMetadata>";

    final BDXR2MarshallerServiceMetadata aMarshaller = new BDXR2MarshallerServiceMetadata ();
    aMarshaller.setUseSchema (true);
    aMarshaller.setValidationEventHandler (new DoNothingValidationEventHandler ());

    // Contains an invalid date
    final ServiceMetadataType aSM = aMarshaller.read (s.getBytes (StandardCharsets.UTF_8));
    assertNull (aSM);
  }
}
