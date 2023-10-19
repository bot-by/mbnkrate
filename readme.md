# mbnkrate

Unofficial wrapper for [Monobank Currency Rate API][api].

[![Codacy Grade](https://app.codacy.com/project/badge/Grade/b5717f0b18bc4794a56dfe6859562eed)](https://app.codacy.com/gl/bot-by/mbnkrate/dashboard?utm_source=gl&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![Codacy Coverage](https://app.codacy.com/project/badge/Coverage/b5717f0b18bc4794a56dfe6859562eed)](https://app.codacy.com/gl/bot-by/mbnkrate/dashboard?utm_source=gl&utm_medium=referral&utm_content=&utm_campaign=Badge_coverage)
[![Java Version](https://img.shields.io/static/v1?label=java&message=11&color=blue&logo=java&logoColor=E23D28)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

## Getting started

The Monobank Currency Rate API is a public service and is free to use.
Currency rates are updated every 5 minutes, and the API has a rate limit of once per minute.
To avoid your client from being banned, consider caching the response.

## Contributing

Please read [Contributing](contributing.md).

## History

See [Changelog](changelog.md)

## License

    Copyright 2022-2023 Vitalij Berdinskih

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)
[Apache License v2.0](LICENSE)

[api]: https://api.monobank.ua/docs/#tag/Publichni-dani/paths/~1bank~1currency/get "Отримання курсів валют"