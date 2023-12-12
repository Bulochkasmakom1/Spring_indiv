package com.example.crm.controllers;

import com.example.crm.data.entities.Participant;
import com.example.crm.data.repositories.ParticipantRepository;
import com.example.crm.services.ParticipantService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/participant")
public class ParticipantWebController {

    @Autowired
    private final ParticipantRepository participantRepository;

    @Autowired
    private final ParticipantService participantService;

    public ParticipantWebController(ParticipantRepository participantRepository, ParticipantService participantService) {
        this.participantRepository = participantRepository;
        this.participantService = participantService;
    }


    @GetMapping
    public String index() {
        return "/participant/index.html";
    }

    @RequestMapping(value = "/data_for_datatable", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getDataForDatatable(@RequestParam Map<String, Object> params) {
        int draw = params.containsKey("draw") ? Integer.parseInt(params.get("draw").toString()) : 1;
        int length = params.containsKey("length") ? Integer.parseInt(params.get("length").toString()) : 30;
        int start = params.containsKey("start") ? Integer.parseInt(params.get("start").toString()) : 30;
        int currentPage = start / length;

        String sortName = "id";
        String dataTableOrderColumnIdx = params.get("order[0][column]").toString();
        String dataTableOrderColumnName = "columns[" + dataTableOrderColumnIdx + "][data]";
        if (params.containsKey(dataTableOrderColumnName))
            sortName = params.get(dataTableOrderColumnName).toString();
        String sortDir = params.containsKey("order[0][dir]") ? params.get("order[0][dir]").toString() : "asc";

        Sort.Order sortOrder = new Sort.Order((sortDir.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC), sortName);
        Sort sort = Sort.by(sortOrder);

        Pageable pageRequest = PageRequest.of(currentPage,
                length,
                sort);

        String queryString = (String) (params.get("search[value]"));

        Page<Participant> participants = participantService.getParticipantsForDatatable(queryString, pageRequest);

        long totalRecords = participants.getTotalElements();

        List<Map<String, Object>> cells = new ArrayList<>();
        participants.forEach(participant -> {
            Map<String, Object> cellData = new HashMap<>();
            cellData.put("id", participant.getId());
            cellData.put("child", participant.getChild());
            cellData.put("dateBirth", participant.getDateBirth());
            cellData.put("city", participant.getCity());
            cellData.put("country", participant.getCountry());
            cellData.put("parent", participant.getParent());
            cellData.put("emailAddress", participant.getEmailAddress());
            cellData.put("phoneNumber", participant.getPhoneNumber());
            cells.add(cellData);
        });

        Map<String, Object> jsonMap = new HashMap<>();

        jsonMap.put("draw", draw);
        jsonMap.put("recordsTotal", totalRecords);
        jsonMap.put("recordsFiltered", totalRecords);
        jsonMap.put("data", cells);

        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(jsonMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Participant participantInstance = participantRepository.findById(Long.valueOf(id)).get();

        model.addAttribute("participantInstance", participantInstance);

        return "/participant/edit.html";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("participantInstance") Participant participantInstance,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes atts) {
        if (bindingResult.hasErrors()) {
            return "/participant/edit.html";
        } else {
            if (participantRepository.save(participantInstance) != null)
                atts.addFlashAttribute("message", "Participant updated successfully");
            else
                atts.addFlashAttribute("message", "Participant update failed.");

            return "redirect:/participant/";
        }
    }

    @GetMapping("/create")
    public String create(Model model)
    {
        model.addAttribute("participantInstance", new Participant());
        return "/participant/create.html";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("participantInstance") Participant participantInstance,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes atts) {
        if (bindingResult.hasErrors()) {
            return "/participant/create.html";
        } else {
            if (participantRepository.save(participantInstance) != null)
                atts.addFlashAttribute("message", "Participant created successfully");
            else
                atts.addFlashAttribute("message", "Participant creation failed.");

            return "redirect:/participant/";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes atts) {
        Participant participantInstance = participantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Participant Not Found:" + id));

        participantRepository.delete(participantInstance);

        atts.addFlashAttribute("message", "Participant deleted.");

        return "redirect:/participant/";
    }

}
