package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

/**
 * The Enum WildCardEligible The Blacklist Type eligible for WildCard.
 */
public enum WildCardEligible {
   
   /** The ipaddress. */
   IPADDRESS,
   
   /** The email. */
   EMAIL;
   
 

   /**
    * Contains.
    *
    * @param type the type
    * @return true, if successful
    */
   public static boolean contains(String type) {

       for (WildCardEligible wildCardEligible : WildCardEligible.values()) {
           if (wildCardEligible.name().equalsIgnoreCase(type)) {
               return true;
           }
       }

       return false;
   }
}


