package ro.ps.proiect.view.view;


import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;
import ro.ps.proiect.presenter.gui_interfaces.I_TrenView;
import ro.ps.proiect.presenter.presenter.TrenPresenter;
import ro.ps.proiect.presenter.dto.GaraDTO;
import ro.ps.proiect.presenter.dto.TrenDTO;
import ro.ps.proiect.view.layout.MainLayout;

import java.util.Optional;

@PageTitle("Trains")
@Route(value = "/trains", layout = MainLayout.class)
@Component
@UIScope
public class TrenView extends VerticalLayout implements I_TrenView {
    private final TrenPresenter trenPresenter;

    private final TextField nrTrenTextField = new TextField("Train Number");
    private Select<GaraDTO> garaDePlecareSelect = new Select<>();
    private Select<GaraDTO> garaDeSosireSelect = new Select<>();
    private final TextField searchByNrTrenTextField = new TextField("Search by Number");
    private final Button addTrenButton;
    private final Button updateTrenButton;
    private final Button deleteTrenButton;
    private final Button searchByNrTrenButton;
    private final Button showVagoaneButton;
    private final Button showOrarGaraButton;
    private Grid<TrenDTO> trenuriGrid = new Grid<>();
    private final Dialog dialogBox = new Dialog();
    private final NativeLabel dialogMessage = new NativeLabel();
    private final Button okButton;

    public TrenView(TrenPresenter trenPresenter){
        this.trenPresenter = trenPresenter;
        trenPresenter.setI_trenView_and_init_gridView_and_selects(this);

        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.add(dialogMessage);
        dialogBox.add(dialogLayout);

        okButton = new Button("Ok", e -> dialogBox.close());
        dialogBox.getFooter().add(okButton);

        addTrenButton = new Button("Add", this::saveTren);
        updateTrenButton = new Button("Update", this::updateTren);
        deleteTrenButton = new Button("Delete", this::deleteTren);
        searchByNrTrenButton = new Button("Search", this::searchByNrTren);
        showVagoaneButton = new Button("Show Wagons", this::redirectVagonView);
        showOrarGaraButton = new Button("Show Schedule", this::redirectOrarGaraView);

        this.addClassName("centered-content");

        VerticalLayout layout = new VerticalLayout();

        HorizontalLayout grid_and_searchLayout = new HorizontalLayout();
        VerticalLayout searchLayout = new VerticalLayout();
        searchLayout.add(searchByNrTrenTextField, searchByNrTrenButton);
        searchLayout.setWidth("300px");
        trenuriGrid.addClassName("grid");

        grid_and_searchLayout.add(trenuriGrid);
        grid_and_searchLayout.add(searchLayout);
        grid_and_searchLayout.addClassName("grid-filters");

        layout.add(grid_and_searchLayout);


        HorizontalLayout textFieldsLayout = new HorizontalLayout(nrTrenTextField, garaDePlecareSelect, garaDeSosireSelect);
        textFieldsLayout.addClassName("textfields");
        layout.add(textFieldsLayout);

        HorizontalLayout buttonsLayout = new HorizontalLayout(addTrenButton, updateTrenButton, deleteTrenButton, showVagoaneButton, showOrarGaraButton);
        buttonsLayout.addClassName("buttons");
        addTrenButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        updateTrenButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        deleteTrenButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        showVagoaneButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        showOrarGaraButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        searchByNrTrenButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        layout.add(buttonsLayout);

        this.add(layout);
    }

    //event handling
    private void saveTren(ClickEvent clickEvent){
        trenPresenter.saveTren();
    }

    private void updateTren(ClickEvent clickEvent){
        trenPresenter.updateTren();
    }

    private void deleteTren(ClickEvent clickEvent){
        trenPresenter.deleteTren();
    }

    private void searchByNrTren(ClickEvent clickEvent){
        trenPresenter.searchByNrTren();
    }

    private void redirectVagonView(ClickEvent clickEvent){
        trenPresenter.redirectVagonView();
    }

    private void redirectOrarGaraView(ClickEvent clickEvent){
        trenPresenter.redirectOrarGaraView();
    }

    //interface methods
    @Override
    public Long getSelectedTrenId() {
        Optional<TrenDTO> trenDTO = trenuriGrid.getSelectedItems().stream().findFirst();
        return trenDTO.map(TrenDTO::id).orElse(null);
    }

    @Override
    public String getNrTren() {
        return nrTrenTextField.getValue();
    }

    @Override
    public String getSearchNrTren(){
        return searchByNrTrenTextField.getValue();
    }

    @Override
    public String getSelectedNrTren() {
        Optional<TrenDTO> trenDTO = trenuriGrid.getSelectedItems().stream().findFirst();
        return trenDTO.map(TrenDTO::nrTren).orElse(null);
    }

    @Override
    public GaraDTO getGaraDePlecare() {
        return garaDePlecareSelect.getValue();
    }

    @Override
    public GaraDTO getSelectedGaraDePlecare() {
        Optional<TrenDTO> trenDTO = trenuriGrid.getSelectedItems().stream().findFirst();
        return trenDTO.map(TrenDTO::garaDePlecare).orElse(null);
    }

    @Override
    public GaraDTO getGaraDeSosire() {
        return garaDeSosireSelect.getValue();
    }

    @Override
    public GaraDTO getSelectedGaraDeSosire() {
        Optional<TrenDTO> trenDTO = trenuriGrid.getSelectedItems().stream().findFirst();
        return trenDTO.map(TrenDTO::garaDeSosire).orElse(null);
    }

    @Override
    public void setMessage(String title, String message) {
        dialogBox.setHeaderTitle(title);
        dialogMessage.removeAll();
        dialogMessage.add(message);

        dialogBox.open();
    }

    @Override
    public void setGridView(Grid<TrenDTO> newTrenuriGrid) {
        trenuriGrid = newTrenuriGrid;
        trenuriGrid.getDataProvider().refreshAll();
    }

    @Override
    public void setGaraSelects(Select<GaraDTO> newGaraDePlecareSelect, Select<GaraDTO> newGaraDeSosireSelect) {
        garaDePlecareSelect = newGaraDePlecareSelect;
        garaDePlecareSelect.getDataProvider().refreshAll();
        garaDeSosireSelect = newGaraDeSosireSelect;
        garaDeSosireSelect.getDataProvider().refreshAll();
    }

}
