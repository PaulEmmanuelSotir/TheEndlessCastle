package game.dataAccessLayer;

public class AssetLicense
{
	public AssetLicense(String licenseShortName, String licenseSummary, String hyperlink) {
		LicenseShortName = licenseShortName;
		LicenseSummary = licenseSummary;
		Hyperlink = hyperlink;
		
	}
	
	public AssetLicense(String licenseShortName, String licenseSummary) {
		LicenseShortName = licenseShortName;
		LicenseSummary = licenseSummary;
		Hyperlink = null;
	}

	public String LicenseShortName;
	public String LicenseSummary;
	public String Hyperlink;
}
