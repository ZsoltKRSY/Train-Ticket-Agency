package ro.ps.proiect.presenter.gui_interfaces;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.select.Select;
import ro.ps.proiect.presenter.dto.GaraDTO;
import ro.ps.proiect.presenter.dto.OrarGaraDTO;

import java.time.LocalTime;

public interface I_OrarGaraView {

    Long getSelectedOrarGaraId();
    GaraDTO getGara();
    GaraDTO getSelectedGara();
    LocalTime getOraDeSosire();
    LocalTime getSelectedOraDeSosire();
    LocalTime getOraDePlecare();
    LocalTime getSelectedOraDePlecare();
    Integer getTraseu();
    Integer getSelectedTraseu();
    void setMessage(String title, String message);
    void setGridView(Grid<OrarGaraDTO> grid);
    void setGaraSelect(Select<GaraDTO> garaSelect);
}
