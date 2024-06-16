package com.airfrance.batch.unsubscribecomprefkl.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UnsubscribeComprefInputTest {
   @Test
   void testActionIndex() {
      UnsubscribeComprefInput input = new UnsubscribeComprefInput();

      assertNull(input.getActionIndex());
      input.setActionIndex("U");
      assertEquals("U", input.getActionIndex());
   }

   @Test
   void testDomainComprefIndex() {
      UnsubscribeComprefInput input = new UnsubscribeComprefInput();
      assertNull(input.getDomainComprefIndex());
      input.setDomainComprefIndex("S");
      assertEquals("S", input.getDomainComprefIndex());
   }

   @Test
   void testComGroupTypeComprefIndex() {
      UnsubscribeComprefInput input = new UnsubscribeComprefInput();

      assertNull(input.getComGroupTypeComprefIndex());
      input.setComGroupTypeComprefIndex("N");
      assertEquals("N", input.getComGroupTypeComprefIndex());
   }

   @Test
   void testComTypeComprefIndex() {
      UnsubscribeComprefInput input = new UnsubscribeComprefInput();
      assertNull(input.getComTypeComprefIndex());
      input.setComTypeComprefIndex("KL");
      assertEquals("KL", input.getComTypeComprefIndex());
   }

   @Test
   void testGinIndex() {
      UnsubscribeComprefInput input = new UnsubscribeComprefInput();
      assertNull(input.getGinIndex());
      input.setGinIndex("123456789");
      assertEquals("123456789", input.getGinIndex());
   }

   @Test
   void testMarketComprefIndex() {
      UnsubscribeComprefInput input = new UnsubscribeComprefInput();
      assertNull(input.getMarketComprefIndex());
      input.setMarketComprefIndex("EN");
      assertEquals("EN", input.getMarketComprefIndex());
   }

   @Test
   void testLanguageComprefIndex() {
      UnsubscribeComprefInput input = new UnsubscribeComprefInput();
      assertNull(input.getLanguageComprefIndex());
      input.setLanguageComprefIndex("FR");
      assertEquals("FR", input.getLanguageComprefIndex());
   }

   @Test
   void testCauseIndex() {
      UnsubscribeComprefInput input = new UnsubscribeComprefInput();
      assertNull(input.getCauseIndex());
      input.setCauseIndex("007");
      assertEquals("007", input.getCauseIndex());
   }
}
