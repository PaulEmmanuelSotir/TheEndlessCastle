package game.dataAccessLayer;

public class AssetLicense
{
	public AssetLicense(String licenseShortName, String licenseSummary, String creditsText) {
		LicenseShortName = licenseShortName;
		LicenseSummary = licenseSummary;
		CreditsText = creditsText;
	}
	
	public AssetLicense(String licenseShortName, String licenseSummary) {
		LicenseShortName = licenseShortName;
		LicenseSummary = licenseSummary;
		CreditsText = null;
	}
	
	public String LicenseShortName;
	public String LicenseSummary;
	public String CreditsText;
}
