package ro.ps.proiect.presenter.dto;

import lombok.Builder;

import java.time.LocalTime;

@Builder
public record OrarGaraDTO(Long id, GaraDTO garaDTO, LocalTime oraDeSosire, LocalTime oraDePlecare, Integer traseu) {
}
