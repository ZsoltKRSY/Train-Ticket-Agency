package ro.ps.proiect.presenter.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record BiletDTO(Long id, LocalDate dataCalatoriei, GaraDTO garaDePlecare, GaraDTO garaDeDestinatie,
                       LocalTime oraDePlecare, LocalTime oraDeSosire, TrenDTO tren, VagonDTO vagon, int nrLoc) {
}
