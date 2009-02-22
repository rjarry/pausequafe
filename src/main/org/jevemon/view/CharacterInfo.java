package org.jevemon.view;

import org.jevemon.model.charactersheet.CharacterSheet;

import com.trolltech.qt.core.QRect;
import com.trolltech.qt.gui.QFrame;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;

public class CharacterInfo extends QFrame {

	private Ui_CharacterInfo ui = new Ui_CharacterInfo();

	private QLabel portrait;
	private QLabel info;
	private QLabel skills;

	public CharacterInfo() {
		this(null);
	}

	public CharacterInfo(QWidget parent) {
		super(parent);
		ui.setupUi(this);
		setupUi();
	}

	private void setupUi() {
		setFrameShape(QFrame.Shape.Panel);
		setFrameShadow(QFrame.Shadow.Plain);
		QRect rect = new QRect(
				5,5,this.width()-10,this.height()-10);
		setFrameRect(rect);
		
		portrait = (QLabel) this.findChild(QLabel.class, "portrait");
		info = (QLabel) this.findChild(QLabel.class, "info");
		skills = (QLabel) this.findChild(QLabel.class, "skills");

	}

	public void loadPortrait(String fileName) {
		QPixmap image = new QPixmap();
		image.load(fileName);

		int size = portrait.height();
		portrait.setPixmap(image.scaledToHeight(size));
	}

	public void loadInfo(CharacterSheet sheet) {


	}

}
