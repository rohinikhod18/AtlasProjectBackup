
/* Update USA country in KYC GBGroup Attribute in Compliance_ServiceProviderAttribute table */

UPDATE Comp.Compliance_ServiceProviderAttribute
SET [Attribute]='{"endPointUrl":"https://pilot.id3global.com/ID3gWS/ID3global.svc/Soap11_Auth","qname":"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd","userName":"admin2@currenciesdirect.com","passWord":"N3wP4ssw0rd123!!2019","profileVersionIDList":[{"country":"UNITED KINGDOM","id":"49693f46-adf1-4d23-99ed-b0c9ac9a41cb"},{"country":"DENMARK","id":"fd68ba1e-d392-4356-8cef-888c91f1c425"},{"country":"SWITZERLAND","id":"d375bc86-076e-4a2a-91f3-268b5e5d51b7"},{"country":"GERMANY","id":"67057787-b24a-40bb-8aff-8106fa9cf1e9"},{"country":"NETHERLANDS","id":"ee35856f-dabe-4f80-82f3-bbad0639301b"},{"country":"SPAIN","id":"f7248eed-e3ef-47c6-a92b-052bce97a960"},{"country":"SWEDEN","id":"1616b0ac-cfc0-4141-a668-3b4a4bf712ea"},{"country":"NORWAY","id":"38ddcd30-2e48-44c9-bd23-71c1802053f7"},{"country":"USA","id":"1079207b-3d20-4178-be95-9a3415a447b9"}],"socketTimeoutMillis":25000,"connectionTimeoutMillis":25000, "poaRequired":false,"poiRequired":false,"alwaysPass":false,"hlFlag":false}',UpdatedOn=CURRENT_TIMESTAMP 
WHERE ID=3 

/* Insert USA country under GBGroup provider */

INSERT INTO Comp.KYC_CountryProviderMapping
(Country, ProviderName, CreatedOn, CreatedBy, UpdatedOn, UpdatedBy)
VALUES('USA', 'GBGroup', CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, 1);
