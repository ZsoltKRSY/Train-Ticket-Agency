package ro.ps.proiect.view.view;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;
import ro.ps.proiect.presenter.gui_interfaces.I_GaraView;
import ro.ps.proiect.presenter.presenter.GaraPresenter;
import ro.ps.proiect.presenter.dto.GaraDTO;
import ro.ps.proiect.view.layout.MainLayout;

import java.util.Optional;

@PageTitle("Train Stations")
@Route(value = "/stations", layout = MainLayout.class)
@Component
@UIScope
public class GaraView extends VerticalLayout implements I_GaraView {

    private final GaraPresenter garaPresenter;

    private final TextField judetTextField = new TextField("County");
    private final TextField localitateTextField = new TextField("City");
    private final TextField adresaStradaTextField = new TextField("Street address");
    private final Button addGaraButton;
    private final Button updateGaraButton;
    private final Button deleteGaraButton;
    private Grid<GaraDTO> gariGrid = new Grid<>();
    private final Dialog dialogBox = new Dialog();
    private final NativeLabel dialogMessage = new NativeLabel();
    private final Button okButton;

    public GaraView(GaraPresenter garaPresenter){
        this.garaPresenter = garaPresenter;
        garaPresenter.setI_garaView_and_init_gridView(this);

        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.add(dialogMessage);
        dialogBox.add(dialogLayout);

        okButton = new Button("Ok", e -> dialogBox.close());
        dialogBox.getFooter().add(okButton);

        addGaraButton = new Button("Add", this::saveGara);
        updateGaraButton = new Button("Update", this::updateGara);
        deleteGaraButton = new Button("Delete", this::deleteGara);

        this.addClassName("centered-content");

        VerticalLayout layout = new VerticalLayout();

        gariGrid.addClassName("grid");
        layout.add(gariGrid);

        HorizontalLayout textFieldsLayout = new HorizontalLayout(judetTextField, localitateTextField, adresaStradaTextField);
        textFieldsLayout.addClassName("textfields");
        layout.add(textFieldsLayout);

        HorizontalLayout buttonsLayout = new HorizontalLayout(addGaraButton, updateGaraButton, deleteGaraButton);
        buttonsLayout.addClassName("buttons");
        addGaraButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        updateGaraButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        deleteGaraButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        layout.add(buttonsLayout);

        this.add(layout);
    }

    //event handling
    private void saveGara(ClickEvent clickEvent){
        garaPresenter.saveGara();
    }

    private void updateGara(ClickEvent clickEvent){
        garaPresenter.updateGara();
    }

    private void deleteGara(ClickEvent clickEvent){
        garaPresenter.deleteGara();
    }

    //i_GaraView methods
    @Override
    public Long getSelectedGaraId() {
        Optional<GaraDTO> garaDTO = gariGrid.getSelectedItems().stream().findFirst();
        return garaDTO.map(GaraDTO::id).orElse(null);
    }

    @Override
    public String getGaraJudet() {
        return judetTextField.getValue();
    }

    @Override
    public String getSelectedGaraJudet() {
        Optional<GaraDTO> garaDTO = gariGrid.getSelectedItems().stream().findFirst();
        return garaDTO.map(GaraDTO::judet).orElse(null);
    }

    @Override
    public String getGaraLocalitate() {
        return localitateTextField.getValue();
    }

    @Override
    public String getSelectedGaraLocalitate() {
        Optional<GaraDTO> garaDTO = gariGrid.getSelectedItems().stream().findFirst();
        return garaDTO.map(GaraDTO::localitate).orElse(null);
    }

    @Override
    public String getGaraAdresaStrada() {
        return adresaStradaTextField.getValue();
    }

    @Override
    public String getSelectedGaraAdresaStrada() {
        Optional<GaraDTO> garaDTO = gariGrid.getSelectedItems().stream().findFirst();
        return garaDTO.map(GaraDTO::adresaStrada).orElse(null);
    }

    @Override
    public void setMessage(String title, String message) {
        dialogBox.setHeaderTitle(title);
        dialogMessage.removeAll();
        dialogMessage.add(message);

        dialogBox.open();
    }

    @Override
    public void setGridView(Grid<GaraDTO> newGariGrid){
        gariGrid = newGariGrid;
        gariGrid.getDataProvider().refreshAll();
    }

}
