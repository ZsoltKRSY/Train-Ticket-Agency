package ro.ps.proiect.presenter.gui_interfaces;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.select.Select;
import ro.ps.proiect.presenter.dto.GaraDTO;
import ro.ps.proiect.presenter.dto.TrenDTO;

public interface I_TrenView {

    Long getSelectedTrenId();
    String getNrTren();
    String getSelectedNrTren();
    String getSearchNrTren();
    GaraDTO getGaraDePlecare();
    GaraDTO getSelectedGaraDePlecare();
    GaraDTO getGaraDeSosire();
    GaraDTO getSelectedGaraDeSosire();
    void setMessage(String title, String message);
    void setGridView(Grid<TrenDTO> grid);
    void setGaraSelects(Select<GaraDTO> garaDePlecareSelect, Select<GaraDTO> garaDeSosireSelect);

}
