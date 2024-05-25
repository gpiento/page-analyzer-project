@import hexlet.code.utils.NamedRoutes;

@template.layout.page(
content = @`
    <div class="container-lg mt-5">
        <h1>Сайты</h1>

        <table class="table table-bordered table-hover mt-3">
            <thead>
            <tr>
                <th class="col-1">ID</th>
                <th>Имя</th>
                <th class="col-2">Последняя проверка</th>
                <th class="col-1">Код ответа</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>
                    1
                </td>
                <td>
                    <a href="/urls/1">https://java-page-analyzer-ru.hexlet.app</a>
                </td>
                <td>
                </td>
                <td>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
`
)
