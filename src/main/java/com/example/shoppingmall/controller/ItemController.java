package com.example.shoppingmall.controller;

import com.example.shoppingmall.dto.ItemDTO;
import com.example.shoppingmall.repository.ItemRepository;
import com.example.shoppingmall.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor //생성자
@RequestMapping("/mall")
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/list")
    public String listForm(Model model){
        List<ItemDTO> itemDTOList = itemService.findAll();
        model.addAttribute("itemList", itemDTOList);
        return "list";
    }

    @GetMapping("/add")
    public String addForm(){
        return "add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute ItemDTO itemDTO) throws IOException {
        System.out.println("itemDTO = "+ itemDTO);
        itemService.add(itemDTO);
        return "redirect:/mall/list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model){
        ItemDTO itemDTO = itemService.findById(id);
        model.addAttribute("item", itemDTO);
        return "detail";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        ItemDTO itemDTO = itemService.findById(id);
        model.addAttribute("itemUpdate", itemDTO);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute ItemDTO itemDTO, Model model) throws IOException {
        itemService.update(itemDTO);
        ItemDTO item = itemService.findById(itemDTO.getId());
        model.addAttribute("item", item);
        return "detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        itemService.delete(id);
        return "redirect:/mall/list";

    }
}
