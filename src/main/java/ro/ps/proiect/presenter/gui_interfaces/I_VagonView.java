package ro.ps.proiect.presenter.gui_interfaces;

import com.vaadin.flow.component.grid.Grid;
import ro.ps.proiect.presenter.dto.VagonDTO;

public interface I_VagonView {

    Long getSelectedVagonId();
    String getNrVagon();
    int getNrTotalLocuri();
    void setMessage(String title, String message);
    void setGridView(Grid<VagonDTO> grid);

}
