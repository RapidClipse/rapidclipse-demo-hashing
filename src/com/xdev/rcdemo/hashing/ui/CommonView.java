package com.xdev.rcdemo.hashing.ui;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Provider;
import java.security.Security;

import org.apache.commons.lang3.text.StrBuilder;
import org.apache.shiro.codec.Hex;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.xdev.security.authentication.jpa.HashStrategy;
import com.xdev.ui.XdevButton;
import com.xdev.ui.XdevGridLayout;
import com.xdev.ui.XdevHorizontalSplitPanel;
import com.xdev.ui.XdevLabel;
import com.xdev.ui.XdevPanel;
import com.xdev.ui.XdevTextArea;
import com.xdev.ui.XdevVerticalLayout;
import com.xdev.ui.XdevView;
import com.xdev.ui.navigation.Navigation;

public class CommonView extends XdevView {

	private final String mypassword = "mypassword123$§";

	/**
	 *
	 */
	public CommonView() {
		super();
		this.initUI();
		this.labelCDValue.setValue(Charset.defaultCharset().toString());


	}

	@Override
	public void enter(final ViewChangeListener.ViewChangeEvent event) {
		super.enter(event);

	}

	/**
	 * Event handler delegate method for the {@link XdevButton}
	 * {@link #buttonProvider}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void buttonProvider_buttonClick(final Button.ClickEvent event) {

		final StrBuilder sb = new StrBuilder();

		// Get all available provider
		final Provider[] providers = Security.getProviders();


		for (int i = 0; i < providers.length; i++)
		{
			final Provider provider = providers[i];
			sb.appendln("---");

			// print provider info
			sb.appendln("Provider: " + provider.getInfo());

			sb.appendln("Available services:");

			// get provider's services and print it's algorithm
			provider.getServices().forEach(s -> {

				sb.appendln(" -> " + s.getAlgorithm());

			});
		}

		addToTextarea(sb.toString(), true);
	}


	private void addToTextarea(final String text, final boolean reset)
	{
		this.textArea.setReadOnly(false);
		if (reset) {
			this.textArea.setValue(text);
		}
		else
		{
			this.textArea.setValue(this.textArea.getValue() + "\n" + text);
		}
		this.textArea.setReadOnly(true);
	}

	/**
	 * Event handler delegate method for the {@link XdevButton} {@link #button}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void button_buttonClick(final Button.ClickEvent event) {
		Navigation.to("").navigate();
	}

	/**
	 * Event handler delegate method for the {@link XdevButton} {@link #rcAndMD5}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void rcAndMD5_buttonClick(final Button.ClickEvent event) {

		final StrBuilder sb = new StrBuilder();

		final byte[] hashedPassword = new HashStrategy.MD5().hashPassword(this.mypassword.getBytes());

		sb.appendln("RC HashStrategy with MD5");
		sb.appendln(new String(hashedPassword));
		sb.appendln(Hex.encodeToString(hashedPassword));

		addToTextarea(sb.toString(), true);
	}

	/**
	 * Event handler delegate method for the {@link XdevButton}
	 * {@link #rcAndPBKDF2}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void rcAndPBKDF2_buttonClick(final Button.ClickEvent event) {

		final StrBuilder sb = new StrBuilder();

		final byte[] hashedPassword = new HashStrategy.PBKDF2WithHmacSHA1().hashPassword(this.mypassword.getBytes());

		sb.appendln("RC HashStrategy with PBKDF2WithHmacSHA1");
		sb.appendln(new String(hashedPassword));
		sb.appendln(Hex.encodeToString(hashedPassword));

		addToTextarea(sb.toString(), true);

	}

	/**
	 * Event handler delegate method for the {@link XdevButton} {@link #rcAndISO}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void rcAndISO_buttonClick(final Button.ClickEvent event) {
		final StrBuilder sb = new StrBuilder();

		final byte[] hashedPassword = new HashStrategy.SHA2().hashPassword(this.mypassword.getBytes(Charset.forName("ISO-8859-1")));

		sb.appendln("RC HashStrategy and ISO-8859-1");
		sb.appendln(new String(hashedPassword));
		sb.appendln(Hex.encodeToString(hashedPassword));

		addToTextarea(sb.toString(), true);
	}

	/**
	 * Event handler delegate method for the {@link XdevButton} {@link #rcAndUTF16}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void rcAndUTF16_buttonClick(final Button.ClickEvent event) {
		final StrBuilder sb = new StrBuilder();

		final byte[] hashedPassword = new HashStrategy.SHA2().hashPassword(this.mypassword.getBytes(StandardCharsets.UTF_16));

		sb.appendln("RC HashStrategy and UTF-16");
		sb.appendln(new String(hashedPassword));
		sb.appendln(Hex.encodeToString(hashedPassword));

		addToTextarea(sb.toString(), true);
	}

	/**
	 * Event handler delegate method for the {@link XdevButton} {@link #rcAndUTF8}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void rcAndUTF8_buttonClick(final Button.ClickEvent event) {
		final StrBuilder sb = new StrBuilder();

		final byte[] hashedPassword = new HashStrategy.SHA2().hashPassword(this.mypassword.getBytes(StandardCharsets.UTF_8));

		sb.appendln("Default: RC HashStrategy and UTF-8");
		sb.appendln(new String(hashedPassword));
		sb.appendln(Hex.encodeToString(hashedPassword));

		addToTextarea(sb.toString(), true);
	}

	/*
	 * WARNING: Do NOT edit!<br>The content of this method is always regenerated by
	 * the UI designer.
	 */
	// <generated-code name="initUI">
	private void initUI() {
		this.horizontalSplitPanel = new XdevHorizontalSplitPanel();
		this.verticalLayout = new XdevVerticalLayout();
		this.panel2 = new XdevPanel();
		this.verticalLayout5 = new XdevVerticalLayout();
		this.button = new XdevButton();
		this.panel3 = new XdevPanel();
		this.verticalLayout4 = new XdevVerticalLayout();
		this.gridLayout = new XdevGridLayout();
		this.labelCSDesc = new XdevLabel();
		this.labelCDValue = new XdevLabel();
		this.labelPW = new XdevLabel();
		this.label2 = new XdevLabel();
		this.panel = new XdevPanel();
		this.verticalLayout3 = new XdevVerticalLayout();
		this.labelProvider = new XdevLabel();
		this.buttonProvider = new XdevButton();
		this.panel5 = new XdevPanel();
		this.verticalLayout7 = new XdevVerticalLayout();
		this.label4 = new XdevLabel();
		this.rcAndMD5 = new XdevButton();
		this.rcAndPBKDF2 = new XdevButton();
		this.panel4 = new XdevPanel();
		this.verticalLayout6 = new XdevVerticalLayout();
		this.label = new XdevLabel();
		this.rcAndUTF8 = new XdevButton();
		this.rcAndISO = new XdevButton();
		this.rcAndUTF16 = new XdevButton();
		this.verticalLayout2 = new XdevVerticalLayout();
		this.textArea = new XdevTextArea();

		this.horizontalSplitPanel.setStyleName("large");
		this.horizontalSplitPanel.setSplitPosition(80.0F, Unit.PERCENTAGE);
		this.button.setCaption("Back");
		this.gridLayout.setMargin(new MarginInfo(false));
		this.labelCSDesc.setValue("Default Charset");
		this.labelCDValue.setValue("Label");
		this.labelPW.setValue("password");
		this.label2.setValue(this.mypassword);
		this.labelProvider.setValue("List all available provider for the Java Security API and it's services.");
		this.buttonProvider.setCaption("Show provider and services");
		this.label4.setValue("Using default charset (utf-8) with different hashing algorithms");
		this.rcAndMD5.setCaption("Rapidclipse Hashstrategy & md5");
		this.rcAndPBKDF2.setCaption("Rapidclipse Hashstrategy & PBKDF2WithHmacSHA1");
		this.label.setValue("RapidClipse Hashstrategy, SHA256 with different charsets");
		this.rcAndUTF8.setCaption("Default: Rapidclipse Hashstrategy &UTF-8");
		this.rcAndISO.setCaption("Rapidclipse Hashstrategy & ISO-8859-1");
		this.rcAndUTF16.setCaption("Rapidclipse Hashstrategy & UTF-16");
		this.textArea.setReadOnly(true);

		this.button.setSizeUndefined();
		this.verticalLayout5.addComponent(this.button);
		this.verticalLayout5.setWidth(100, Unit.PERCENTAGE);
		this.verticalLayout5.setHeight(-1, Unit.PIXELS);
		this.panel2.setContent(this.verticalLayout5);
		this.gridLayout.setColumns(2);
		this.gridLayout.setRows(3);
		this.labelCSDesc.setWidth(100, Unit.PERCENTAGE);
		this.labelCSDesc.setHeight(-1, Unit.PIXELS);
		this.gridLayout.addComponent(this.labelCSDesc, 0, 1);
		this.gridLayout.setComponentAlignment(this.labelCSDesc, Alignment.TOP_RIGHT);
		this.labelCDValue.setWidth(100, Unit.PERCENTAGE);
		this.labelCDValue.setHeight(-1, Unit.PIXELS);
		this.gridLayout.addComponent(this.labelCDValue, 1, 1);
		this.labelPW.setWidth(100, Unit.PERCENTAGE);
		this.labelPW.setHeight(-1, Unit.PIXELS);
		this.gridLayout.addComponent(this.labelPW, 0, 2);
		this.label2.setWidth(100, Unit.PERCENTAGE);
		this.label2.setHeight(-1, Unit.PIXELS);
		this.gridLayout.addComponent(this.label2, 1, 2);
		this.gridLayout.setColumnExpandRatio(0, 10.0F);
		this.gridLayout.setColumnExpandRatio(1, 10.0F);
		this.gridLayout.setWidth(100, Unit.PERCENTAGE);
		this.gridLayout.setHeight(-1, Unit.PIXELS);
		this.verticalLayout4.addComponent(this.gridLayout);
		this.verticalLayout4.setComponentAlignment(this.gridLayout, Alignment.TOP_CENTER);
		this.verticalLayout4.setWidth(100, Unit.PERCENTAGE);
		this.verticalLayout4.setHeight(-1, Unit.PIXELS);
		this.panel3.setContent(this.verticalLayout4);
		this.labelProvider.setWidth(100, Unit.PERCENTAGE);
		this.labelProvider.setHeight(-1, Unit.PIXELS);
		this.verticalLayout3.addComponent(this.labelProvider);
		this.buttonProvider.setSizeUndefined();
		this.verticalLayout3.addComponent(this.buttonProvider);
		this.verticalLayout3.setWidth(100, Unit.PERCENTAGE);
		this.verticalLayout3.setHeight(-1, Unit.PIXELS);
		this.panel.setContent(this.verticalLayout3);
		this.label4.setWidth(100, Unit.PERCENTAGE);
		this.label4.setHeight(-1, Unit.PIXELS);
		this.verticalLayout7.addComponent(this.label4);
		this.rcAndMD5.setSizeUndefined();
		this.verticalLayout7.addComponent(this.rcAndMD5);
		this.rcAndPBKDF2.setSizeUndefined();
		this.verticalLayout7.addComponent(this.rcAndPBKDF2);
		this.verticalLayout7.setWidth(100, Unit.PERCENTAGE);
		this.verticalLayout7.setHeight(-1, Unit.PIXELS);
		this.panel5.setContent(this.verticalLayout7);
		this.label.setSizeUndefined();
		this.verticalLayout6.addComponent(this.label);
		this.rcAndUTF8.setSizeUndefined();
		this.verticalLayout6.addComponent(this.rcAndUTF8);
		this.rcAndISO.setSizeUndefined();
		this.verticalLayout6.addComponent(this.rcAndISO);
		this.rcAndUTF16.setSizeUndefined();
		this.verticalLayout6.addComponent(this.rcAndUTF16);
		this.verticalLayout6.setWidth(100, Unit.PERCENTAGE);
		this.verticalLayout6.setHeight(-1, Unit.PIXELS);
		this.panel4.setContent(this.verticalLayout6);
		this.panel2.setWidth(100, Unit.PERCENTAGE);
		this.panel2.setHeight(-1, Unit.PIXELS);
		this.verticalLayout.addComponent(this.panel2);
		this.panel3.setWidth(100, Unit.PERCENTAGE);
		this.panel3.setHeight(-1, Unit.PIXELS);
		this.verticalLayout.addComponent(this.panel3);
		this.verticalLayout.setComponentAlignment(this.panel3, Alignment.MIDDLE_CENTER);
		this.panel.setWidth(100, Unit.PERCENTAGE);
		this.panel.setHeight(-1, Unit.PIXELS);
		this.verticalLayout.addComponent(this.panel);
		this.verticalLayout.setComponentAlignment(this.panel, Alignment.MIDDLE_CENTER);
		this.panel5.setWidth(100, Unit.PERCENTAGE);
		this.panel5.setHeight(-1, Unit.PIXELS);
		this.verticalLayout.addComponent(this.panel5);
		this.verticalLayout.setComponentAlignment(this.panel5, Alignment.MIDDLE_CENTER);
		this.panel4.setWidth(100, Unit.PERCENTAGE);
		this.panel4.setHeight(-1, Unit.PIXELS);
		this.verticalLayout.addComponent(this.panel4);
		this.verticalLayout.setComponentAlignment(this.panel4, Alignment.MIDDLE_CENTER);
		final CustomComponent verticalLayout_spacer = new CustomComponent();
		verticalLayout_spacer.setSizeFull();
		this.verticalLayout.addComponent(verticalLayout_spacer);
		this.verticalLayout.setExpandRatio(verticalLayout_spacer, 1.0F);
		this.textArea.setSizeFull();
		this.verticalLayout2.addComponent(this.textArea);
		this.verticalLayout2.setComponentAlignment(this.textArea, Alignment.MIDDLE_CENTER);
		this.verticalLayout2.setExpandRatio(this.textArea, 10.0F);
		this.verticalLayout.setSizeFull();
		this.horizontalSplitPanel.setFirstComponent(this.verticalLayout);
		this.verticalLayout2.setSizeFull();
		this.horizontalSplitPanel.setSecondComponent(this.verticalLayout2);
		this.horizontalSplitPanel.setSizeFull();
		this.setContent(this.horizontalSplitPanel);
		this.setSizeFull();

		this.button.addClickListener(event -> this.button_buttonClick(event));
		this.buttonProvider.addClickListener(event -> this.buttonProvider_buttonClick(event));
		this.rcAndMD5.addClickListener(event -> this.rcAndMD5_buttonClick(event));
		this.rcAndPBKDF2.addClickListener(event -> this.rcAndPBKDF2_buttonClick(event));
		this.rcAndUTF8.addClickListener(event -> this.rcAndUTF8_buttonClick(event));
		this.rcAndISO.addClickListener(event -> this.rcAndISO_buttonClick(event));
		this.rcAndUTF16.addClickListener(event -> this.rcAndUTF16_buttonClick(event));
	} // </generated-code>

	// <generated-code name="variables">
	private XdevButton button, buttonProvider, rcAndMD5, rcAndPBKDF2, rcAndUTF8, rcAndISO, rcAndUTF16;
	private XdevLabel labelCSDesc, labelCDValue, labelPW, label2, labelProvider, label4, label;
	private XdevTextArea textArea;
	private XdevPanel panel2, panel3, panel, panel5, panel4;
	private XdevGridLayout gridLayout;
	private XdevVerticalLayout verticalLayout, verticalLayout5, verticalLayout4, verticalLayout3, verticalLayout7,
			verticalLayout6, verticalLayout2;
	private XdevHorizontalSplitPanel horizontalSplitPanel;
	// </generated-code>

}
