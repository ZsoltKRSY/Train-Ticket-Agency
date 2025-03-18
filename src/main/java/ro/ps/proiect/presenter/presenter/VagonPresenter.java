package ro.ps.proiect.presenter.presenter;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import org.springframework.stereotype.Component;
import ro.ps.proiect.model.data_structures.Tren;
import ro.ps.proiect.model.data_structures.Vagon;
import ro.ps.proiect.model.repository.TrenRepository;
import ro.ps.proiect.model.repository.VagonRepository;
import ro.ps.proiect.presenter.gui_interfaces.I_VagonView;
import ro.ps.proiect.presenter.dto.VagonDTO;
import ro.ps.proiect.presenter.dto.mapper.VagonMapper;
import java.util.List;
import java.util.Optional;

@Component
public class VagonPresenter {

    private final VagonRepository vagonRepository;
    private final TrenRepository trenRepository;
    private String nrTren = "";
    private I_VagonView i_vagonView;
    private final Grid<VagonDTO> vagoaneGrid = new Grid<>();

    public VagonPresenter(VagonRepository vagonRepository, TrenRepository trenRepository){
        this.vagonRepository = vagonRepository;
        this.trenRepository = trenRepository;

        this.initVagoaneGrid();
    }

    public void setI_vagonView_and_init_gridView(I_VagonView i_vagonView, String nrTren){
        this.nrTren = nrTren;
        this.i_vagonView = i_vagonView;

        this.setVagoaneGrid(nrTren);
        this.i_vagonView.setGridView(vagoaneGrid);
    }

    public void saveVagon(){
        Vagon vagon = this.vagonAddCheck();
        if(vagon != null) {
            try {
                vagonRepository.save(vagon);
                i_vagonView.setMessage("Success!", "Successfully added new wagon!");

                this.setVagoaneGrid(nrTren);
                this.i_vagonView.setGridView(vagoaneGrid);
            }
            catch (Exception ex) {
                i_vagonView.setMessage("Failure!", "A wagon with this number already exists!");
            }
        }
    }

    public void deleteVagon(){
        Long id = i_vagonView.getSelectedVagonId();
        if(id == null){
            i_vagonView.setMessage("Failure!", "No wagon is selected!");
        }
        else {
            try {
                vagonRepository.deleteById(id);
                i_vagonView.setMessage("Success!", "Successfully removed wagon!");

                this.setVagoaneGrid(nrTren);
                this.i_vagonView.setGridView(vagoaneGrid);
            }
            catch (Exception ex) {
                i_vagonView.setMessage("Failure!", "There are other records depending on information about this wagon!");
            }
        }
    }

    private Vagon vagonAddCheck(){
        if(i_vagonView.getNrVagon().isEmpty()){
            i_vagonView.setMessage("Failure!", "Input fields cannot be empty!");
            return null;
        }
        String nrVagon = i_vagonView.getNrVagon();

        if(i_vagonView.getNrTotalLocuri() < 0){
            i_vagonView.setMessage("Failure!", "Invalid input data!");
            return null;
        }
        int nrTotalLocuri = i_vagonView.getNrTotalLocuri();

        Tren currentTren = trenRepository.findByNrTren(nrTren).orElse(null);

        return new Vagon(null, nrVagon, nrTotalLocuri, currentTren);
    }

    private void redirectTrenView(){
        UI.getCurrent().navigate("trains");
    }

    private void initVagoaneGrid(){
        vagoaneGrid.addColumn(VagonDTO::nrVagon).setHeader("Wagon Number");
        vagoaneGrid.addColumn(VagonDTO::trenDTO).setHeader("Train Number");
        vagoaneGrid.addColumn(VagonDTO::nrLocuriLibere).setHeader("Number of Free Seats");
    }

    private void setVagoaneGrid(String nrTren){
        Optional<Tren> currentTren = trenRepository.findByNrTren(nrTren);
        if(currentTren.isPresent()){
            List<VagonDTO> vagonDTOs = VagonMapper.vagonEntityListToDTOs(vagonRepository.findAllByTren(currentTren.get()));
            vagoaneGrid.setItems(vagonDTOs);
        }
        else{
            i_vagonView.setMessage("Error!", "Couldn't fetch information about the train with this Number!");
            this.redirectTrenView();
        }
    }

}
