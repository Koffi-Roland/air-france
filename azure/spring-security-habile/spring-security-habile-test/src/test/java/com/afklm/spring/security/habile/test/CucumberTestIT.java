package com.afklm.spring.security.habile.test;

import io.cucumber.junit.Cucumber;
import io.cucumber.spring.CucumberContextConfiguration;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@SpringBootTest(classes = ITConfiguration.class)
public class CucumberTestIT {}
