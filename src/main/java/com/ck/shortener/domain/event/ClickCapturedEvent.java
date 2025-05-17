package com.ck.shortener.domain.event;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Fires each time the redirect endpoint is hit.
 */

public record ClickCapturedEvent(String shortCode, HttpServletRequest request) { }