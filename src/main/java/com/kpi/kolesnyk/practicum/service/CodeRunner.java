package com.kpi.kolesnyk.practicum.service;

import java.security.Principal;
import java.util.Map;

public interface CodeRunner {
    Map<String, Integer> estimate(Principal principal, Long taskId, String code);
}
