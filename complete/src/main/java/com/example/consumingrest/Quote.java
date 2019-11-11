package com.example.consumingrest;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {

	@JsonAlias("type")
    private String quoteType;
	@JsonAlias("value")
    private QuotedValue quotedValue;

    public Quote() {
    }

	public String getQuoteType() {
		return quoteType;
	}

	public void setQuoteType(String quoteType) {
		this.quoteType = quoteType;
	}

	public QuotedValue getQuotedValue() {
		return quotedValue;
	}

	public void setQuotedValue(QuotedValue quotedValue) {
		this.quotedValue = quotedValue;
	}

	@Override
	public String toString() {
		return "Quote [quoteType=" + quoteType + ", quotedValue=" + quotedValue + "]";
	}

}