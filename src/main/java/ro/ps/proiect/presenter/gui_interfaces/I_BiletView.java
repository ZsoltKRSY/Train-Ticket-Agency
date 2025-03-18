package ro.ps.proiect.presenter.gui_interfaces;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.server.StreamResource;
import ro.ps.proiect.presenter.dto.*;

import java.time.LocalDate;
import java.time.LocalTime;

public interface I_BiletView {

    Long getSelectedBiletId();
    LocalDate getDataCalatoriei();
    LocalDate getSelectedDataCalatoriei();
    GaraDTO getGaraDePlecare();
    GaraDTO getSelectedGaraDePlecare();
    GaraDTO getGaraDeDestinatie();
    GaraDTO getSelectedGaraDeDestinatie();
    LocalTime getOraDePlecare();
    LocalTime getSelectedOraDePlecare();
    LocalTime getOraDeSosire();
    LocalTime getSelectedOraDeSosire();
    OreDTO getOraDePlecareSiSosire();
    VagonDTO getVagon();
    VagonDTO getSelectedVagon();
    int getNrLoc();
    int getSelectedNrLoc();
    void setMessage(String title, String message);
    void setGridView(Grid<BiletDTO> grid);
    void setGaraSelects(Select<GaraDTO> garaDePlecareSelect, Select<GaraDTO> garaDeDestinatieSelect);
    void setOraDePlecareSiSosireSelect(Select<OreDTO> oraDePlecareSiSosireSelect);
    void setTrenTextField(String nrTren);
    void setVagonSelect(Select<VagonDTO> vagonSelect);
    void setNrLocIntegerField(int nrLoc);
    LocalDate getFilterDataCalatorieiDatePicker();
    TrenDTO getFilterTrenSelect();
    GaraDTO getFilterGaraDeDestinatieSelect();
    void setFilterTrenSelect(Select<TrenDTO> filterTrenSelect);
    void setFilterGaraDeDestinatieSelect(Select<GaraDTO> filterGaraDeDestinatieSelect);
    String getSalvareListaFormatSelect();
    void salvareListaBilete(StreamResource streamResource, String format);

}
