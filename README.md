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
If you not do this, the report will presents errors. Why? Because is Birt!


-----------------------------------------------------------------------

-> Como usar?

Depois de clonar o repositório, você deve baixar o Birt Designer para construir seus relatórios.
https://eclipse-birt.github.io/birt-website/docs/installation/

Salve seus relatórios no diretório "reports". Para chamar, você deve submeter um JSON para a API com o nome do seu 
relatório e parâmetros.

Você pode usar esse JSON para ver o resultado:
{
    "report": "demo.rptdesign",
    "p_test": "test1234"
}

-> ATENÇÃO!

Você deve abrir o XML Source do seu relatório e modificar a propriedade "version" (linha 2) para "3.2.23".
Se você não fizer isto, o relatório irá apresentar erros. Por quê? Porque é Birt!
