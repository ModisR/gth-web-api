# Gallifreyan Translation Helper Web API

The goal of this project is to provide a more convenient interface with which the [Discord bot](https://github.com/Mightyfrong/gth-discord-bot) can interact. Currently, it uses the clunkier approach of [puppeteer-ing](https://github.com/puppeteer/puppeteer) the [Gallifreyan Translation Helper web page](https://github.com/Mightyfrong/gallifreyan-translation-helper).

This API is in the proof of concept stage, merely demonstrating the ability to accept a JSON request and echo it back in an SVG response.

## Architecture

This API is not [RESTful](https://en.wikipedia.org/wiki/Representational_state_transfer) since it does not manage some internally persisted state. It more closely resembles [version 1.0 of the JSON-RPC](https://en.wikipedia.org/wiki/JSON-RPC#Version_1.0) protocol with some differences. These differences will be documented in more detail in the future as the project evolves into a more stable state.