# BirtAPI

-> How to use?

After cloning the repository, you must download the Birt Designer for build your reports.
https://eclipse-birt.github.io/birt-website/docs/installation/

Save your reports on path "reports". For call, you must send a JSON for API with name of your report and parameters.

You can use this JSON for look the results:
{
    "report": "demo.rptdesign",
    "p_test": "test1234"
}

-> WARNING!

You must open the XML Source of your report and change the property "version" (line 2) for "3.2.23".
If you not do this, the report will presents errors.