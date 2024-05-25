@import hexlet.code.utils.NamedRoutes;

@template.layout.page(
content = @`
    <div class="container-lg mt-5">
        <h1>Сайт: https://www.youtube.com</h1>

        <table class="table table-bordered table-hover mt-3">
            <tbody>
            <tr>
                <td>ID</td>
                <td>2</td>
            </tr>
            <tr>
                <td>Имя</td>
                <td>https://www.youtube.com</td>
            </tr>
            <tr>
                <td>Дата создания</td>
                <td>25/05/2024 15:04</td>
            </tr>
            </tbody>
        </table>

        <h2 class="mt-5">Проверки</h2>
        <form method="post" action="/urls/2/checks">
            <button type="submit" class="btn btn-primary">Запустить проверку
            </button>
        </form>

        <table class="table table-bordered table-hover mt-3">
            <thead>
            <th class="col-1">ID</th>
            <th class="col-1">Код ответа</th>
            <th>title</th>
            <th>h1</th>
            <th>description</th>
            <th class="col-2">Дата проверки</th>
            </thead>
            <tbody>

            </tbody>
        </table>
    </div>
`
)
