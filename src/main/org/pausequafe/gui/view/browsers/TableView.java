package org.pausequafe.gui.view.browsers;

import com.trolltech.qt.gui.QTableView;
import com.trolltech.qt.gui.QWidget;

public class TableView extends QTableView {

	public TableView() {
	}

	public TableView(QWidget parent) {
		super(parent);
	}

	@Override
	public int sizeHintForRow(int row) {
		return 0;
	}


}
