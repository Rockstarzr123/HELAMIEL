(() => {
    if (!window.bootstrap) {
        return;
    }

    const tooltipTriggerList = document.querySelectorAll("[title]");
    [...tooltipTriggerList].forEach((tooltipTriggerEl) => {
        new bootstrap.Tooltip(tooltipTriggerEl);
    });
})();
