package com.xdev.rcdemo.hashing.ui;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.text.StrBuilder;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;

import com.vaadin.data.Property;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.xdev.rcdemo.hashing.business.MyAuthenticationProvider;
import com.xdev.security.authentication.jpa.HashStrategy;
import com.xdev.security.authentication.jpa.JPAAuthenticator;
import com.xdev.ui.XdevAccordion;
import com.xdev.ui.XdevButton;
import com.xdev.ui.XdevCheckBox;
import com.xdev.ui.XdevGridLayout;
import com.xdev.ui.XdevHorizontalLayout;
import com.xdev.ui.XdevHorizontalSplitPanel;
import com.xdev.ui.XdevLabel;
import com.xdev.ui.XdevPanel;
import com.xdev.ui.XdevTextArea;
import com.xdev.ui.XdevVerticalLayout;
import com.xdev.ui.XdevView;
import com.xdev.ui.entitycomponent.listselect.XdevOptionGroup;
import com.xdev.ui.navigation.Navigation;

public class HashDemoView extends XdevView {

	List<String> choices = new ArrayList<>();

	private final String mypassword = "mypassword123$";

	/**
	 *
	 */
	public HashDemoView() {
		super();
		this.initUI();

		this.labelCDValue.setValue(Charset.defaultCharset().toString());
		this.labelDefaultPassword.setValue(this.mypassword);

		this.choices.add("shortened");
		this.choices.add("detailed");

		this.optionRCICondense.addItems(this.choices);
		this.optionRCICondense.setValue("shortened");

		this.optionRCIICondense.addItems(this.choices);
		this.optionRCIICondense.setValue("shortened");

		this.optionMDCondense.addItems(this.choices);
		this.optionMDCondense.setValue("shortened");




	}

	@Override
	public void enter(final ViewChangeListener.ViewChangeEvent event) {
		super.enter(event);

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
	 * Event handler delegate method for the {@link XdevButton} {@link #backButton}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void backButton_buttonClick(final Button.ClickEvent event) {
		Navigation.to("").navigate();
	}



	/**
	 * Event handler delegate method for the {@link XdevButton}
	 * {@link #buttonRCIHash}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void buttonRCIHash_buttonClick(final Button.ClickEvent event) {

		final StrBuilder sb = new StrBuilder();


		if (this.optionRCICondense.getValue().equals("detailed"))
		{

			// Get the password as bytearray
			final byte[] passwordAsByteArray = this.mypassword.getBytes();

			// Get an instance of the provider
			final MyAuthenticationProvider myAuthProvider = MyAuthenticationProvider.getInstance();

			// Get the provider's authenticator
			final JPAAuthenticator authenticator = (JPAAuthenticator) myAuthProvider.provideAuthenticator();

			// Get the HashStrategy
			final HashStrategy hashStrategy = authenticator.getHashStrategy();

			// Now hash the password
			final byte[] hashedPassword = hashStrategy.hashPassword(passwordAsByteArray);



			sb.appendln(" ------------------------------------- ");
			sb.appendln("Rapidclipse - with HashStrategy from AuthenticationProvider");
			sb.appendln("[detailed]");
			sb.appendln(" ------------------------------------- ");
			sb.appendln("The password hash with \"new String(hashedPassword)\":");
			sb.appendln("\t" + new String(hashedPassword));
			sb.appendln(" ------------------------------------- ");
			sb.appendln("The password hash as hex representation:");
			sb.appendln("\t" + Hex.encodeToString(hashedPassword));
		}
		else
		{

			// All-in-one with Rapidclipse's Hashstrategy from the provider
			final byte[] hashedPassword = ((JPAAuthenticator)MyAuthenticationProvider.getInstance().provideAuthenticator())
				.getHashStrategy()
				.hashPassword(this.mypassword.getBytes());



			sb.appendln(" ------------------------------------- ");
			sb.appendln("Rapidclipse - with Authenticator from AuthenticationProvider");
			sb.appendln("[shortened]");
			sb.appendln(" ------------------------------------- ");
			sb.appendln("The password hash with \"new String(hashedPassword)\":");
			sb.appendln("\t" + new String(hashedPassword));
			sb.appendln(" ------------------------------------- ");
			sb.appendln("The password hash as hex representation:");
			sb.appendln("\t" + Hex.encodeToString(hashedPassword));
		}

		addToTextarea(sb.toString(), true);
		System.out.println(sb.toString());
	}






	/**
	 * Event handler delegate method for the {@link XdevButton} {@link #buttonRCII}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void buttonRCII_buttonClick(final Button.ClickEvent event) {

		final StrBuilder sb = new StrBuilder();


		if (this.optionRCIICondense.getValue().equals("detailed"))
		{

			// Get the password as bytearray
			final byte[] passwordAsByteArray = this.mypassword.getBytes();

			// Get a new SHA-256 HashStrategy object
			final HashStrategy hashStrategy = new HashStrategy.SHA2();

			// Now hash the password
			final byte[] hashedPassword = hashStrategy.hashPassword(passwordAsByteArray);


			sb.appendln(" ------------------------------------- ");
			sb.appendln("Rapidclipse - with new HashStrategy object");
			sb.appendln("[detailed]");
			sb.appendln(" ------------------------------------- ");
			sb.appendln("The password hash with \"new String(hashedPassword)\":");
			sb.appendln("\t" + new String(hashedPassword));
			sb.appendln(" ------------------------------------- ");
			sb.appendln("The password hash as hex representation:");
			sb.appendln("\t" + Hex.encodeToString(hashedPassword));
		}
		else
		{

			// All-in-one with new Hashstrategy object
			final byte[] hashedPassword = new HashStrategy.SHA2().hashPassword(this.mypassword.getBytes());


			sb.appendln(" ------------------------------------- ");
			sb.appendln("Rapidclipse -  with new HashStrategy object");
			sb.appendln("[shortened]");
			sb.appendln(" ------------------------------------- ");
			sb.appendln("The password hash with \"new String(hashedPassword)\":");
			sb.appendln("\t" + new String(hashedPassword));
			sb.appendln(" ------------------------------------- ");
			sb.appendln("The password hash as hex representation:");
			sb.appendln("\t" + Hex.encodeToString(hashedPassword));
		}

		addToTextarea(sb.toString(), true);
		System.out.println(sb.toString());

	}







	/**
	 * Event handler delegate method for the {@link XdevButton} {@link #buttonMD}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void buttonMD_buttonClick(final Button.ClickEvent event) {

		try {

			final StrBuilder sb = new StrBuilder();


			if (this.optionMDCondense.getValue().equals("detailed"))
			{

				// Get the password as bytearray
				final byte[] passwordAsByteArray = this.mypassword.getBytes();

				// Get the MessageDigest instance for SHA-256
				final MessageDigest mySha256Instance = MessageDigest.getInstance("SHA-256");

				// Update the instance with the current password
				mySha256Instance.update(passwordAsByteArray);

				// Get the hash for the password (here: called digest)
				final byte[] hashedPassword = mySha256Instance.digest();



				sb.appendln(" ------------------------------------- ");
				sb.appendln("Hashing a password with the MessageDigest class");
				sb.appendln("[detailed]");
				sb.appendln(" ------------------------------------- ");
				sb.appendln("The password hash with \"new String(hashedPassword)\":");
				sb.appendln("\t" + new String(hashedPassword));
				sb.appendln(" ------------------------------------- ");
				sb.appendln("The password hash as hex representation:");
				sb.appendln("\t" + Hex.encodeToString(hashedPassword));
			}
			else
			{

				// All-in-one with a SHA-256 MessageDigest instance
				final byte[] hashedPassword = MessageDigest.getInstance("SHA-256").digest(this.mypassword.getBytes());



				sb.appendln(" ------------------------------------- ");
				sb.appendln("Hashing a password with the MessageDigest class");
				sb.appendln("[shortened]");
				sb.appendln(" ------------------------------------- ");
				sb.appendln("The password hash with \"new String(hashedPassword)\":");
				sb.appendln("\t" + new String(hashedPassword));
				sb.appendln(" ------------------------------------- ");
				sb.appendln("The password hash as hex representation:");
				sb.appendln("\t" + Hex.encodeToString(hashedPassword));
			}

			addToTextarea(sb.toString(), true);
			System.out.println(sb.toString());


		} catch (final NoSuchAlgorithmException e) {
			Notification.show("A error has occurred: No such algorithm", Type.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * Event handler delegate method for the {@link XdevButton}
	 * {@link #buttonShiro}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void buttonShiro_buttonClick(final Button.ClickEvent event) {

		final StrBuilder sb = new StrBuilder();


		if (!this.checkBox.getValue())
		{
			/*
			 *  Checkbox not checked -> hash without salt
			 */

			// Get a Shiro Sha256Hash object
			final Sha256Hash sha256Hash = new Sha256Hash(this.mypassword);

			// Get this hash as a byte array
			final byte[] passwordAsByteArray = sha256Hash.getBytes();



			// Get a hex representation with "toHex()"-method of the Shiro hash object
			final String hexEncoded = sha256Hash.toHex();

			sb.appendln(" ------------------------------------- ");
			sb.appendln("Hashing a password with the Shiro framework class");
			sb.appendln("[without salt]");
			sb.appendln(" ------------------------------------- ");
			sb.appendln("The password hash with \"new String(hashedPassword)\":");
			sb.appendln("\t" + new String(passwordAsByteArray));
			sb.appendln(" ------------------------------------- ");
			sb.appendln("The password hash as hex representation:");
			sb.appendln("\t" + Hex.encodeToString(passwordAsByteArray));
			sb.appendln(" ------------------------------------- ");
			sb.appendln("The password hash as hex representation created with the \"toHex()\"-method of the Shiro hash object");
			sb.appendln("\t" + hexEncoded);
		}
		else
		{
			/*
			 *  Checkbox not checked -> hash with a salt
			 *
			 *  The salt has to be stored along with the hash. It is necessary to validate a password!
			 */

			// Create a salt - for further dtails see documentation of the Shiro framework

			final RandomNumberGenerator rng = new SecureRandomNumberGenerator();
			final ByteSource salt = rng.nextBytes();


			// Get a Shiro Sha256Hash object - WITH salt
			final Sha256Hash sha256HashWithSalt = new Sha256Hash(this.mypassword, salt);

			// Get this hash as a byte array
			final byte[] passwordAsByteArray = sha256HashWithSalt.getBytes();


			// Get a hex representation with "toHex()"-method of the Shiro hash object
			final String hexEncoded = sha256HashWithSalt.toHex();

			sb.appendln(" ------------------------------------- ");
			sb.appendln("Hashing a password with the Shiro framework class");
			sb.appendln("[with salt: \"" + salt.toHex() + "\"]");
			sb.appendln(" ------------------------------------- ");
			sb.appendln("The password hash with \"new String(hashedPassword)\":");
			sb.appendln("\t" + new String(passwordAsByteArray));
			sb.appendln(" ------------------------------------- ");
			sb.appendln("The password hash as hex representation:");
			sb.appendln("\t" + Hex.encodeToString(passwordAsByteArray));
			sb.appendln(" ------------------------------------- ");
			sb.appendln("The password hash as hex representation created with the \"toHex()\"-method of the Shiro hash object");
			sb.appendln("\t" + hexEncoded);
		}

		addToTextarea(sb.toString(), true);
		System.out.println(sb.toString());
	}








	/**
	 * Event handler delegate method for the {@link XdevOptionGroup}
	 * {@link #optionRCICondense}.
	 *
	 * @see Property.ValueChangeListener#valueChange(Property.ValueChangeEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void optionRCICondense_valueChange(final Property.ValueChangeEvent event) {
		if (event.getProperty().getValue().equals("shortened"))
		{
			// TODO
			// set Text
		}
		else
		{

		}
	}



	/*
	 * WARNING: Do NOT edit!<br>The content of this method is always regenerated by
	 * the UI designer.
	 */
	// <generated-code name="initUI">
	private void initUI() {
		this.horizontalSplitPanel = new XdevHorizontalSplitPanel();
		this.verticalLayout = new XdevVerticalLayout();
		this.panel = new XdevPanel();
		this.verticalLayout3 = new XdevVerticalLayout();
		this.backButton = new XdevButton();
		this.panelInfos = new XdevPanel();
		this.horizontalLayout2 = new XdevHorizontalLayout();
		this.gridLayout = new XdevGridLayout();
		this.labelCSDesc = new XdevLabel();
		this.labelCDValue = new XdevLabel();
		this.label = new XdevLabel();
		this.labelDefaultPassword = new XdevLabel();
		this.accordion = new XdevAccordion();
		this.verticalLayoutRCI = new XdevVerticalLayout();
		this.labelRCIStrategyDesc = new XdevLabel();
		this.labelRCIAlgo = new XdevLabel();
		this.optionRCICondense = new XdevOptionGroup<>();
		this.horizontalLayout = new XdevHorizontalLayout();
		this.buttonRCIHash = new XdevButton();
		this.labelRCIDesc = new XdevLabel();
		this.verticalLayoutRCII = new XdevVerticalLayout();
		this.labelRCIIStrategyDesc = new XdevLabel();
		this.labelRCIIAlgo = new XdevLabel();
		this.optionRCIICondense = new XdevOptionGroup<>();
		this.buttonRCII = new XdevButton();
		this.labelRCIIdesc = new XdevLabel();
		this.verticalLayoutMD = new XdevVerticalLayout();
		this.labelMDStrategyDesc = new XdevLabel();
		this.labelMDAlgo = new XdevLabel();
		this.optionMDCondense = new XdevOptionGroup<>();
		this.buttonMD = new XdevButton();
		this.labelMDdesc = new XdevLabel();
		this.verticalLayoutSH = new XdevVerticalLayout();
		this.labelShiroStrategyDesc = new XdevLabel();
		this.labelShiroAlgo = new XdevLabel();
		this.checkBox = new XdevCheckBox();
		this.buttonShiro = new XdevButton();
		this.labelShiroDesc = new XdevLabel();
		this.verticalLayout2 = new XdevVerticalLayout();
		this.textArea = new XdevTextArea();

		this.horizontalSplitPanel.setStyleName("large");
		this.horizontalSplitPanel.setSplitPosition(80.0F, Unit.PERCENTAGE);
		this.backButton.setCaption("Back");
		this.gridLayout.setMargin(new MarginInfo(false));
		this.labelCSDesc.setValue("Default Charset");
		this.labelCDValue.setValue("UTF-8");
		this.label.setValue("password used in the examples");
		this.labelDefaultPassword.setValue(this.mypassword);
		this.labelRCIStrategyDesc.setValue("Charset: default charset");
		this.labelRCIAlgo.setValue("Algorithm: SHA256");
		this.optionRCICondense.setStyleName("horizontal");
		this.horizontalLayout.setMargin(new MarginInfo(false));
		this.buttonRCIHash.setCaption("show");
		this.labelRCIDesc.setValue("Hash with the HashStrategy from the AuthenticationProvider.");
		this.labelRCIIStrategyDesc.setValue("Charset: default charset");
		this.labelRCIIAlgo.setValue("Algorithm: SHA256");
		this.optionRCIICondense.setStyleName("horizontal");
		this.buttonRCII.setCaption("show");
		this.labelRCIIdesc.setValue("Hash with a new HashStrategy");
		this.labelMDStrategyDesc.setValue("Charset: default charset");
		this.labelMDAlgo.setValue("Algorithm: SHA256");
		this.optionMDCondense.setStyleName("horizontal");
		this.buttonMD.setCaption("show");
		this.labelMDdesc.setValue("Hash a password with a new MessageDigest object");
		this.labelShiroStrategyDesc.setValue("Charset: default charset");
		this.labelShiroAlgo.setValue("Algorithm: SHA256");
		this.checkBox.setCaption("with salt");
		this.buttonShiro.setCaption("show");
		this.labelShiroDesc.setValue("With SHA256 class");
		this.textArea.setReadOnly(true);

		this.backButton.setSizeUndefined();
		this.verticalLayout3.addComponent(this.backButton);
		this.verticalLayout3.setWidth(100, Unit.PERCENTAGE);
		this.verticalLayout3.setHeight(-1, Unit.PIXELS);
		this.panel.setContent(this.verticalLayout3);
		this.gridLayout.setColumns(2);
		this.gridLayout.setRows(2);
		this.labelCSDesc.setSizeUndefined();
		this.gridLayout.addComponent(this.labelCSDesc, 0, 0);
		this.gridLayout.setComponentAlignment(this.labelCSDesc, Alignment.TOP_RIGHT);
		this.labelCDValue.setSizeUndefined();
		this.gridLayout.addComponent(this.labelCDValue, 1, 0);
		this.label.setSizeUndefined();
		this.gridLayout.addComponent(this.label, 0, 1);
		this.gridLayout.setComponentAlignment(this.label, Alignment.MIDDLE_RIGHT);
		this.labelDefaultPassword.setSizeUndefined();
		this.gridLayout.addComponent(this.labelDefaultPassword, 1, 1);
		this.gridLayout.setComponentAlignment(this.labelDefaultPassword, Alignment.MIDDLE_LEFT);
		this.gridLayout.setColumnExpandRatio(0, 10.0F);
		this.gridLayout.setColumnExpandRatio(1, 10.0F);
		this.gridLayout.setWidth(100, Unit.PERCENTAGE);
		this.gridLayout.setHeight(-1, Unit.PIXELS);
		this.horizontalLayout2.addComponent(this.gridLayout);
		this.horizontalLayout2.setComponentAlignment(this.gridLayout, Alignment.MIDDLE_CENTER);
		this.horizontalLayout2.setExpandRatio(this.gridLayout, 10.0F);
		this.horizontalLayout2.setSizeFull();
		this.panelInfos.setContent(this.horizontalLayout2);
		this.buttonRCIHash.setSizeUndefined();
		this.horizontalLayout.addComponent(this.buttonRCIHash);
		final CustomComponent horizontalLayout_spacer = new CustomComponent();
		horizontalLayout_spacer.setSizeFull();
		this.horizontalLayout.addComponent(horizontalLayout_spacer);
		this.horizontalLayout.setExpandRatio(horizontalLayout_spacer, 1.0F);
		this.labelRCIStrategyDesc.setWidth(100, Unit.PERCENTAGE);
		this.labelRCIStrategyDesc.setHeight(-1, Unit.PIXELS);
		this.verticalLayoutRCI.addComponent(this.labelRCIStrategyDesc);
		this.labelRCIAlgo.setWidth(100, Unit.PERCENTAGE);
		this.labelRCIAlgo.setHeight(-1, Unit.PIXELS);
		this.verticalLayoutRCI.addComponent(this.labelRCIAlgo);
		this.optionRCICondense.setWidth(100, Unit.PERCENTAGE);
		this.optionRCICondense.setHeight(-1, Unit.PIXELS);
		this.verticalLayoutRCI.addComponent(this.optionRCICondense);
		this.horizontalLayout.setWidth(100, Unit.PERCENTAGE);
		this.horizontalLayout.setHeight(-1, Unit.PIXELS);
		this.verticalLayoutRCI.addComponent(this.horizontalLayout);
		this.labelRCIDesc.setSizeFull();
		this.verticalLayoutRCI.addComponent(this.labelRCIDesc);
		this.verticalLayoutRCI.setComponentAlignment(this.labelRCIDesc, Alignment.MIDDLE_CENTER);
		this.verticalLayoutRCI.setExpandRatio(this.labelRCIDesc, 10.0F);
		this.labelRCIIStrategyDesc.setWidth(100, Unit.PERCENTAGE);
		this.labelRCIIStrategyDesc.setHeight(-1, Unit.PIXELS);
		this.verticalLayoutRCII.addComponent(this.labelRCIIStrategyDesc);
		this.labelRCIIAlgo.setWidth(100, Unit.PERCENTAGE);
		this.labelRCIIAlgo.setHeight(-1, Unit.PIXELS);
		this.verticalLayoutRCII.addComponent(this.labelRCIIAlgo);
		this.optionRCIICondense.setSizeUndefined();
		this.verticalLayoutRCII.addComponent(this.optionRCIICondense);
		this.buttonRCII.setSizeUndefined();
		this.verticalLayoutRCII.addComponent(this.buttonRCII);
		this.labelRCIIdesc.setSizeFull();
		this.verticalLayoutRCII.addComponent(this.labelRCIIdesc);
		this.verticalLayoutRCII.setExpandRatio(this.labelRCIIdesc, 10.0F);
		this.labelMDStrategyDesc.setWidth(100, Unit.PERCENTAGE);
		this.labelMDStrategyDesc.setHeight(-1, Unit.PIXELS);
		this.verticalLayoutMD.addComponent(this.labelMDStrategyDesc);
		this.labelMDAlgo.setWidth(100, Unit.PERCENTAGE);
		this.labelMDAlgo.setHeight(-1, Unit.PIXELS);
		this.verticalLayoutMD.addComponent(this.labelMDAlgo);
		this.optionMDCondense.setSizeUndefined();
		this.verticalLayoutMD.addComponent(this.optionMDCondense);
		this.buttonMD.setSizeUndefined();
		this.verticalLayoutMD.addComponent(this.buttonMD);
		this.labelMDdesc.setSizeFull();
		this.verticalLayoutMD.addComponent(this.labelMDdesc);
		this.verticalLayoutMD.setExpandRatio(this.labelMDdesc, 10.0F);
		this.labelShiroStrategyDesc.setWidth(100, Unit.PERCENTAGE);
		this.labelShiroStrategyDesc.setHeight(-1, Unit.PIXELS);
		this.verticalLayoutSH.addComponent(this.labelShiroStrategyDesc);
		this.labelShiroAlgo.setWidth(100, Unit.PERCENTAGE);
		this.labelShiroAlgo.setHeight(-1, Unit.PIXELS);
		this.verticalLayoutSH.addComponent(this.labelShiroAlgo);
		this.checkBox.setSizeUndefined();
		this.verticalLayoutSH.addComponent(this.checkBox);
		this.buttonShiro.setSizeUndefined();
		this.verticalLayoutSH.addComponent(this.buttonShiro);
		this.labelShiroDesc.setSizeFull();
		this.verticalLayoutSH.addComponent(this.labelShiroDesc);
		this.verticalLayoutSH.setExpandRatio(this.labelShiroDesc, 10.0F);
		this.verticalLayoutRCI.setSizeFull();
		this.accordion.addTab(this.verticalLayoutRCI, "RapidClipse Hashstrategy (I)", null);
		this.verticalLayoutRCII.setSizeFull();
		this.accordion.addTab(this.verticalLayoutRCII, "RapidClipse Hashstrategy (II)", null);
		this.verticalLayoutMD.setSizeFull();
		this.accordion.addTab(this.verticalLayoutMD, "MessageDigest", null);
		this.verticalLayoutSH.setSizeFull();
		this.accordion.addTab(this.verticalLayoutSH, " Shiro framework", null);
		this.accordion.setSelectedTab(this.verticalLayoutMD);
		this.panel.setWidth(100, Unit.PERCENTAGE);
		this.panel.setHeight(-1, Unit.PIXELS);
		this.verticalLayout.addComponent(this.panel);
		this.panelInfos.setWidth(100, Unit.PERCENTAGE);
		this.panelInfos.setHeight(100, Unit.PIXELS);
		this.verticalLayout.addComponent(this.panelInfos);
		this.verticalLayout.setComponentAlignment(this.panelInfos, Alignment.MIDDLE_CENTER);
		this.accordion.setSizeFull();
		this.verticalLayout.addComponent(this.accordion);
		this.verticalLayout.setComponentAlignment(this.accordion, Alignment.MIDDLE_CENTER);
		this.verticalLayout.setExpandRatio(this.accordion, 100.0F);
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

		this.backButton.addClickListener(event -> this.backButton_buttonClick(event));
		this.optionRCICondense.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(final Property.ValueChangeEvent event) {
				HashDemoView.this.optionRCICondense_valueChange(event);
			}
		});
		this.buttonRCIHash.addClickListener(event -> this.buttonRCIHash_buttonClick(event));
		this.buttonRCII.addClickListener(event -> this.buttonRCII_buttonClick(event));
		this.buttonMD.addClickListener(event -> this.buttonMD_buttonClick(event));
		this.buttonShiro.addClickListener(event -> this.buttonShiro_buttonClick(event));
	} // </generated-code>

	// <generated-code name="variables">
	private XdevButton backButton, buttonRCIHash, buttonRCII, buttonMD, buttonShiro;
	private XdevLabel labelCSDesc, labelCDValue, label, labelDefaultPassword, labelRCIStrategyDesc, labelRCIAlgo,
			labelRCIDesc, labelRCIIStrategyDesc, labelRCIIAlgo, labelRCIIdesc, labelMDStrategyDesc, labelMDAlgo,
			labelMDdesc, labelShiroStrategyDesc, labelShiroAlgo, labelShiroDesc;
	private XdevHorizontalLayout horizontalLayout2, horizontalLayout;
	private XdevOptionGroup<?> optionRCICondense, optionRCIICondense, optionMDCondense;
	private XdevTextArea textArea;
	private XdevPanel panel, panelInfos;
	private XdevCheckBox checkBox;
	private XdevGridLayout gridLayout;
	private XdevVerticalLayout verticalLayout, verticalLayout3, verticalLayoutRCI, verticalLayoutRCII, verticalLayoutMD,
			verticalLayoutSH, verticalLayout2;
	private XdevHorizontalSplitPanel horizontalSplitPanel;
	private XdevAccordion accordion;
	// </generated-code>

}
