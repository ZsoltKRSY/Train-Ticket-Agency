package ro.ps.proiect.view.view;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;
import ro.ps.proiect.presenter.gui_interfaces.I_VagonView;
import ro.ps.proiect.presenter.presenter.VagonPresenter;
import ro.ps.proiect.presenter.dto.VagonDTO;
import ro.ps.proiect.view.MainLayout;

import java.util.Optional;

@PageTitle("Wagons")
@Route(value = "wagons/", layout = MainLayout.class)
@Component
@UIScope
public class VagonView extends VerticalLayout implements I_VagonView, HasUrlParameter<String> {
    private final VagonPresenter vagonPresenter;

    private String nrTren = "";
    private final NativeLabel idTrenLabel = new NativeLabel();
    private final TextField nrVagonTextField = new TextField("Wagon Number");
    private final IntegerField nrTotalLocurIntegerField = new IntegerField("Number of Seats");
    private final Button addVagonButton;
    private final Button removeVagonButton;
    private Grid<VagonDTO> vagoaneGrid = new Grid<>();
    private final Dialog dialogBox = new Dialog();
    private final NativeLabel dialogMessage = new NativeLabel();
    private final Button okButton;

    public VagonView(VagonPresenter vagonPresenter){
        this.vagonPresenter = vagonPresenter;

        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.add(dialogMessage);
        dialogBox.add(dialogLayout);

        okButton = new Button("Ok", e -> dialogBox.close());
        dialogBox.getFooter().add(okButton);

        addVagonButton = new Button("Add", this::saveVagon);
        removeVagonButton = new Button("Remove", this::removeVagon);
    }

    private void initGUI(){
        this.removeAll();
        this.addClassName("centered-content");

        VerticalLayout layout = new VerticalLayout();

        idTrenLabel.removeAll();
        idTrenLabel.add("Wagons of train with Number " + nrTren);
        idTrenLabel.setClassName("title");
        layout.add(idTrenLabel);

        vagoaneGrid.addClassName("grid");
        layout.add(vagoaneGrid);

        HorizontalLayout textFieldsLayout = new HorizontalLayout(nrVagonTextField, nrTotalLocurIntegerField);
        textFieldsLayout.addClassName("textfields");
        layout.add(textFieldsLayout);

        HorizontalLayout buttonsLayout = new HorizontalLayout(addVagonButton, removeVagonButton);
        buttonsLayout.addClassName("buttons");
        addVagonButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        removeVagonButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        layout.add(buttonsLayout);

        this.add(layout);
    }

    private void saveVagon(ClickEvent clickEvent){
        vagonPresenter.saveVagon();
    }

    private void removeVagon(ClickEvent clickEvent){
        vagonPresenter.deleteVagon();
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String nrTren) {
        if(nrTren == null){
            this.setMessage("Error!", "There was an error getting the Number of the selected train!");
        }
        else{
            this.nrTren = nrTren;
            vagonPresenter.setI_vagonView_and_init_gridView(this, nrTren);
            initGUI();
        }
    }

    @Override
    public Long getSelectedVagonId() {
        Optional<VagonDTO> vagonDTO = vagoaneGrid.getSelectedItems().stream().findFirst();
        return vagonDTO.map(VagonDTO::id).orElse(null);
    }

    @Override
    public String getNrVagon() {
        return nrVagonTextField.getValue();
    }

    @Override
    public int getNrTotalLocuri() {
        return nrTotalLocurIntegerField.getValue();
    }

    @Override
    public void setMessage(String title, String message) {
        dialogBox.setHeaderTitle(title);
        dialogMessage.removeAll();
        dialogMessage.add(message);

        dialogBox.open();
    }

    @Override
    public void setGridView(Grid<VagonDTO> grid) {
        vagoaneGrid = grid;
        vagoaneGrid.getDataProvider().refreshAll();
    }

}
