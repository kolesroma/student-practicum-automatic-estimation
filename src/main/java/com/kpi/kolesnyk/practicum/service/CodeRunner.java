package com.kpi.kolesnyk.practicum.service;

import java.security.Principal;

public interface CodeRunner {
    String estimate(Principal principal, Long taskId, String code);
}
