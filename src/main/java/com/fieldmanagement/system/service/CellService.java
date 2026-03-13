package com.fieldmanagement.system.service;

import com.fieldmanagement.system.entity.Cell;
import com.fieldmanagement.system.repository.CellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CellService {
    @Autowired
    private CellRepository cellRepository;

    public List<Cell> getAllCells() {
        return cellRepository.findAll();
    }

    public Cell getCellById(Long id) {
        return cellRepository.findById(id).orElse(null);
    }

    public List<Cell> getCellsBySectorId(Long sectorId) {
        return cellRepository.findBySectorId(sectorId);
    }

    public Cell saveCell(Cell cell) {
        return cellRepository.save(cell);
    }

    public void deleteCell(Long id) {
        cellRepository.deleteById(id);
    }
}
