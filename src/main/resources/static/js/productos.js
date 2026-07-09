/* ============================================
   HELAMIEL — Módulo de Gestión de Productos
   Validaciones de formulario, confirmación de
   eliminación y reapertura de modales.
   ============================================ */
(() => {
  'use strict';

  const NOMBRE_REGEX = /^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9 .,'-]+$/;

  /**
   * Valida un formulario de producto en el navegador antes de enviarlo:
   * campos vacíos, solo-espacios, longitudes y caracteres especiales.
   * @param {HTMLFormElement} form formulario a validar.
   * @returns {boolean} true si el formulario es válido.
   */
  function validarFormularioProducto(form) {
    let esValido = true;

    const nombre = form.querySelector('input[name="nombre"]');
    const categoria = form.querySelector('input[name="categoria"]');
    const precio = form.querySelector('input[name="precio"]');
    const stock = form.querySelector('input[name="stock"]');

    [nombre, categoria].forEach((campo) => {
      if (!campo) return;
      const valor = campo.value.trim();
      campo.classList.remove('is-invalid');

      if (valor.length === 0) {
        marcarInvalido(campo, 'Este campo es obligatorio, no puede quedar vacío o con solo espacios.');
        esValido = false;
      } else if (valor.length < 3) {
        marcarInvalido(campo, 'Debe tener al menos 3 caracteres.');
        esValido = false;
      } else if (!NOMBRE_REGEX.test(valor)) {
        marcarInvalido(campo, 'Contiene caracteres especiales no permitidos.');
        esValido = false;
      }
    });

    if (precio) {
      precio.classList.remove('is-invalid');
      const valorPrecio = parseFloat(precio.value);
      if (isNaN(valorPrecio) || valorPrecio <= 0) {
        marcarInvalido(precio, 'El precio debe ser un número mayor a cero.');
        esValido = false;
      }
    }

    if (stock) {
      stock.classList.remove('is-invalid');
      const valorStock = parseInt(stock.value, 10);
      if (isNaN(valorStock) || valorStock < 0) {
        marcarInvalido(stock, 'El stock no puede ser negativo.');
        esValido = false;
      }
    }

    return esValido;
  }

  /**
   * Aplica el estilo de campo inválido y muestra el mensaje de error.
   * @param {HTMLElement} campo campo del formulario.
   * @param {string} mensaje mensaje a mostrar.
   */
  function marcarInvalido(campo, mensaje) {
    campo.classList.add('is-invalid');
    let feedback = campo.parentElement.querySelector('.invalid-feedback');
    if (!feedback) {
      feedback = document.createElement('div');
      feedback.className = 'invalid-feedback';
      campo.parentElement.appendChild(feedback);
    }
    feedback.textContent = mensaje;
    feedback.style.display = 'block';
  }

  document.addEventListener('DOMContentLoaded', () => {
    // Validación al enviar los formularios de registrar/editar producto
    document.querySelectorAll('form.needs-validation-custom').forEach((form) => {
      form.addEventListener('submit', (evento) => {
        if (!validarFormularioProducto(form)) {
          evento.preventDefault();
          evento.stopPropagation();
        }
      });
    });

    // Confirmación antes de eliminar un producto
    document.querySelectorAll('form.form-eliminar').forEach((form) => {
      form.addEventListener('submit', (evento) => {
        const confirmado = window.confirm('¿Seguro que deseas eliminar este producto? Esta acción no se puede deshacer.');
        if (!confirmado) {
          evento.preventDefault();
        }
      });
    });

    // Reabrir el modal correspondiente si el backend reportó errores de validación
    const idModal = document.body.getAttribute('data-abrir-modal');
    if (idModal && idModal !== 'null' && idModal.length > 0) {
      const nombreModal = idModal === 'registrar' ? 'modalRegistrar' : 'modalEditar' + idModal.replace('editar-', '');
      const elementoModal = document.getElementById(nombreModal);
      if (elementoModal && window.bootstrap) {
        new window.bootstrap.Modal(elementoModal).show();
      }
    }
  });
})();
