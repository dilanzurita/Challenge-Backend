package com.challengeBP.accountMovements.controller;

import com.challengeBP.accountMovements.service.ReportService;
import com.challengeBP.accountMovements.service.dto.AccountDTO;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movements/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/{clientId}")
    public Mono<ResponseEntity<List<AccountDTO>>> getAccountStatement(
            @PathVariable("clientId") Long clientId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return reportService.getAccountStatement(clientId, startDate, endDate)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @GetMapping("/{clientId}/excel")
    public Mono<ResponseEntity<byte[]>> downloadExcelReport(
            @PathVariable Long clientId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return reportService.getAccountStatement(clientId, startDate, endDate)
                .map(accounts -> {
                    try (Workbook workbook = new XSSFWorkbook()) {
                        Sheet sheet = workbook.createSheet("Account Statement");

                        int rowNum = 0;
                        for (AccountDTO account : accounts) {
                            Row header = sheet.createRow(rowNum++);
                            header.createCell(0).setCellValue("Account Number");
                            header.createCell(1).setCellValue(account.getAccountNumber());
                            header.createCell(2).setCellValue("Type");
                            header.createCell(3).setCellValue(account.getAccountType());
                            header.createCell(4).setCellValue("Balance");
                            header.createCell(5).setCellValue(account.getCurrentBalance());

                            Row subHeader = sheet.createRow(rowNum++);
                            subHeader.createCell(0).setCellValue("Date");
                            subHeader.createCell(1).setCellValue("Type");
                            subHeader.createCell(2).setCellValue("Value");
                            subHeader.createCell(3).setCellValue("Balance");

                            for (var movement : account.getMovements()) {
                                Row row = sheet.createRow(rowNum++);
                                row.createCell(0).setCellValue(movement.getDate().toString());
                                row.createCell(1).setCellValue(movement.getType());
                                row.createCell(2).setCellValue(movement.getValue());
                                row.createCell(3).setCellValue(movement.getBalance());
                            }

                            rowNum++; // Espacio entre cuentas
                        }

                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        workbook.write(out);

                        return ResponseEntity.ok()
                                .header("Content-Disposition", "attachment; filename=account_statement.xlsx")
                                .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                                .body(out.toByteArray());

                    } catch (Exception e) {
                        return ResponseEntity.internalServerError().build();
                    }
                });
    }

}
