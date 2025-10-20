package com.mchscorp.integrajob.datapi.controller;

import com.mchscorp.integrajob.datapi.dto.*;
import com.mchscorp.integrajob.datapi.service.JobParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobParserController {

    private final JobParserService jobParserService;

    @PostMapping("/parse")
    public ParsedPromptDTO parsePrompt(@RequestBody PromptRequest req) {
        return jobParserService.parsePrompt(req.getPrompt());
    }
}
