package ro.ps.proiect.presenter.gui_interfaces;

import com.vaadin.flow.component.grid.Grid;
import ro.ps.proiect.presenter.dto.GaraDTO;

public interface I_GaraView {
    Long getSelectedGaraId();
    String getGaraJudet();
    String getSelectedGaraJudet();
    String getGaraLocalitate();
    String getSelectedGaraLocalitate();
    String getGaraAdresaStrada();
    String getSelectedGaraAdresaStrada();
    void setMessage(String title, String message);
    void setGridView(Grid<GaraDTO> grid);
}
