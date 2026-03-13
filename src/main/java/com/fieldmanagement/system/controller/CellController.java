package com.fieldmanagement.system.controller;

import com.fieldmanagement.system.entity.Cell;
import com.fieldmanagement.system.service.CellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cells")
public class CellController {
    @Autowired
    private CellService cellService;

    @GetMapping
    public List<Cell> getAllCells() {
        return cellService.getAllCells();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cell> getCellById(@PathVariable Long id) {
        Cell cell = cellService.getCellById(id);
        return cell != null ? ResponseEntity.ok(cell) : ResponseEntity.notFound().build();
    }

    @GetMapping("/sector/{sectorId}")
    public List<Cell> getCellsBySector(@PathVariable Long sectorId) {
        return cellService.getCellsBySectorId(sectorId);
    }

    @PostMapping
    public Cell createCell(@RequestBody Cell cell) {
        return cellService.saveCell(cell);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cell> updateCell(@PathVariable Long id, @RequestBody Cell cell) {
        cell.setId(id);
        return ResponseEntity.ok(cellService.saveCell(cell));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCell(@PathVariable Long id) {
        cellService.deleteCell(id);
        return ResponseEntity.noContent().build();
    }
}
