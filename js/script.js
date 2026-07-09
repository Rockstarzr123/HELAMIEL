/* ============================================
   HELAMIEL — Heladería Artesanal
   JavaScript Principal | Proyecto SENA 2024
   ============================================ */

/* ==============================
   1. LOADER
   ============================== */
window.addEventListener('load', () => {
  const loader = document.getElementById('loader');
  setTimeout(() => {
    loader.classList.add('hidden');
  }, 1200);
});

/* ==============================
   2. NAVBAR — scroll & hamburger
   ============================== */
const navbar    = document.getElementById('navbar');
const hamburger = document.getElementById('hamburger');
const navLinks  = document.getElementById('navLinks');

// Sombra al hacer scroll
window.addEventListener('scroll', () => {
  navbar.classList.toggle('scrolled', window.scrollY > 40);
  // Botón scroll to top
  scrollTopBtn.classList.toggle('show', window.scrollY > 400);
});

// Menú hamburguesa
hamburger.addEventListener('click', () => {
  hamburger.classList.toggle('active');
  navLinks.classList.toggle('open');
});

// Cerrar menú al hacer clic en un enlace
navLinks.querySelectorAll('a').forEach(link => {
  link.addEventListener('click', () => {
    hamburger.classList.remove('active');
    navLinks.classList.remove('open');
  });
});

/* ==============================
   3. SCROLL SUAVE (por si acaso)
   ============================== */
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
  anchor.addEventListener('click', e => {
    const target = document.querySelector(anchor.getAttribute('href'));
    if (target) {
      e.preventDefault();
      target.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  });
});

/* ==============================
   4. SCROLL TO TOP
   ============================== */
const scrollTopBtn = document.getElementById('scrollTop');
scrollTopBtn.addEventListener('click', () => {
  window.scrollTo({ top: 0, behavior: 'smooth' });
});

/* ==============================
   5. CARRITO DE COMPRAS
   ============================== */
let cart = []; // Array para guardar productos

const cartBtn      = document.getElementById('cartBtn');
const cartSidebar  = document.getElementById('cartSidebar');
const cartOverlay  = document.getElementById('cartOverlay');
const closeCartBtn = document.getElementById('closeCart');
const cartCount    = document.getElementById('cartCount');
const cartItemsEl  = document.getElementById('cartItems');
const cartTotalEl  = document.getElementById('cartTotal');
const checkoutBtn  = document.getElementById('checkoutBtn');

// Emojis para los productos (busca el emoji del card)
function getEmojiForProduct(name) {
  const map = {
    'Helado de Fresa':      '🍓',
    'Chocolate Belga':      '🍫',
    'Mango Tropical':       '🥭',
    'Vainilla Premium':     '🍦',
    'Miel y Canela':        '🍯',
    'Arándanos Silvestres': '🫐',
    'Maní Caramelizado':    '🥜',
    'Matcha Latte':         '🍵',
  };
  return map[name] || '🍨';
}

// Abrir / cerrar carrito
function openCart()  {
  cartSidebar.classList.add('open');
  cartOverlay.classList.add('show');
  document.body.style.overflow = 'hidden';
}
function closeCart() {
  cartSidebar.classList.remove('open');
  cartOverlay.classList.remove('show');
  document.body.style.overflow = '';
}

cartBtn.addEventListener('click', openCart);
closeCartBtn.addEventListener('click', closeCart);
cartOverlay.addEventListener('click', closeCart);

// Agregar al carrito
document.querySelectorAll('.btn-add').forEach(btn => {
  btn.addEventListener('click', () => {
    const name  = btn.dataset.name;
    const price = parseInt(btn.dataset.price);
    addToCart(name, price);
  });
});

function addToCart(name, price) {
  // Verificar si ya está en el carrito
  const existing = cart.find(item => item.name === name);
  if (existing) {
    existing.qty++;
  } else {
    cart.push({ name, price, qty: 1, emoji: getEmojiForProduct(name) });
  }
  updateCartUI();
  showToast(`🛒 ${name} agregado al carrito`, 'success');
  openCart();
}

function removeFromCart(name) {
  cart = cart.filter(item => item.name !== name);
  updateCartUI();
}

function changeQty(name, delta) {
  const item = cart.find(i => i.name === name);
  if (!item) return;
  item.qty += delta;
  if (item.qty <= 0) removeFromCart(name);
  else updateCartUI();
}

function updateCartUI() {
  const totalItems = cart.reduce((sum, i) => sum + i.qty, 0);
  const totalPrice = cart.reduce((sum, i) => sum + i.price * i.qty, 0);

  // Contador
  cartCount.textContent = totalItems;
  cartCount.classList.toggle('show', totalItems > 0);

  // Total
  cartTotalEl.textContent = `$${totalPrice.toLocaleString('es-CO')}`;

  // Items en carrito
  if (cart.length === 0) {
    cartItemsEl.innerHTML = '<p class="empty-cart">Tu carrito está vacío 😔</p>';
    return;
  }

  cartItemsEl.innerHTML = cart.map(item => `
    <div class="cart-item">
      <span class="cart-item-emoji">${item.emoji}</span>
      <div class="cart-item-info">
        <strong>${item.name}</strong>
        <span>$${(item.price * item.qty).toLocaleString('es-CO')}</span>
      </div>
      <div class="cart-item-qty">
        <button class="qty-btn" onclick="changeQty('${item.name}', -1)">−</button>
        <span class="qty-num">${item.qty}</span>
        <button class="qty-btn" onclick="changeQty('${item.name}', 1)">+</button>
      </div>
    </div>
  `).join('');
}

// Simular checkout
checkoutBtn.addEventListener('click', () => {
  if (cart.length === 0) {
    showToast('Tu carrito está vacío 😔', 'error');
    return;
  }
  const total = cart.reduce((sum, i) => sum + i.price * i.qty, 0);
  showToast(`✅ Pedido realizado por $${total.toLocaleString('es-CO')}`, 'success');
  cart = [];
  updateCartUI();
  setTimeout(closeCart, 600);
});

/* ==============================
   6. FILTRO DE PRODUCTOS
   ============================== */
const filterBtns = document.querySelectorAll('.filter-btn');
const productCards = document.querySelectorAll('.product-card');

filterBtns.forEach(btn => {
  btn.addEventListener('click', () => {
    // Activar botón
    filterBtns.forEach(b => b.classList.remove('active'));
    btn.classList.add('active');

    const filter = btn.dataset.filter;

    productCards.forEach(card => {
      if (filter === 'todos' || card.dataset.category === filter) {
        card.classList.remove('hidden');
        // Pequeña animación al mostrar
        card.style.animation = 'none';
        card.offsetHeight; // reflow
        card.style.animation = '';
      } else {
        card.classList.add('hidden');
      }
    });
  });
});

/* ==============================
   7. VALIDACIÓN DEL FORMULARIO
   ============================== */
const contactForm = document.getElementById('contactForm');

contactForm.addEventListener('submit', e => {
  e.preventDefault();

  const nombre  = document.getElementById('nombre');
  const email   = document.getElementById('email');
  const mensaje = document.getElementById('mensaje');

  let valid = true;

  // Limpiar errores previos
  clearError('nombre');
  clearError('email');
  clearError('mensaje');

  // Validar nombre (mín. 3 caracteres)
  if (nombre.value.trim().length < 3) {
    showError('nombre', 'El nombre debe tener al menos 3 caracteres.');
    valid = false;
  }

  // Validar email
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email.value.trim())) {
    showError('email', 'Ingresa un correo electrónico válido.');
    valid = false;
  }

  // Validar mensaje (mín. 10 caracteres)
  if (mensaje.value.trim().length < 10) {
    showError('mensaje', 'El mensaje debe tener al menos 10 caracteres.');
    valid = false;
  }

  if (valid) {
    // Simular envío exitoso
    showToast('✅ ¡Mensaje enviado con éxito! Te contactaremos pronto.', 'success');
    contactForm.reset();
  } else {
    showToast('⚠️ Por favor corrige los errores del formulario.', 'error');
  }
});

function showError(fieldId, msg) {
  const field = document.getElementById(fieldId);
  const errorEl = document.getElementById(fieldId + 'Error');
  field.classList.add('error');
  if (errorEl) errorEl.textContent = msg;
}

function clearError(fieldId) {
  const field = document.getElementById(fieldId);
  const errorEl = document.getElementById(fieldId + 'Error');
  field.classList.remove('error');
  if (errorEl) errorEl.textContent = '';
}

/* ==============================
   8. TOAST NOTIFICATION
   ============================== */
let toastTimeout;

function showToast(msg, type = '') {
  const toast = document.getElementById('toast');
  toast.textContent = msg;
  toast.className = 'toast show';
  if (type) toast.classList.add(type);

  clearTimeout(toastTimeout);
  toastTimeout = setTimeout(() => {
    toast.className = 'toast';
  }, 3200);
}

/* ==============================
   9. ANIMACIONES AL HACER SCROLL
   ============================== */
const revealElements = document.querySelectorAll('.reveal');

const revealObserver = new IntersectionObserver((entries) => {
  entries.forEach(entry => {
    if (entry.isIntersecting) {
      entry.target.classList.add('visible');
      // Solo animar una vez
      revealObserver.unobserve(entry.target);
    }
  });
}, { threshold: 0.15 });

revealElements.forEach(el => revealObserver.observe(el));

// También revelar product cards al entrar en pantalla
const cardObserver = new IntersectionObserver((entries) => {
  entries.forEach((entry, i) => {
    if (entry.isIntersecting) {
      setTimeout(() => {
        entry.target.style.opacity = '1';
        entry.target.style.transform = 'translateY(0)';
      }, i * 80);
      cardObserver.unobserve(entry.target);
    }
  });
}, { threshold: 0.1 });

productCards.forEach(card => {
  card.style.opacity = '0';
  card.style.transform = 'translateY(24px)';
  card.style.transition = 'opacity .5s ease, transform .5s ease, box-shadow .3s ease';
  cardObserver.observe(card);
});

/* ==============================
   10. BOTONES DE PROMO
   ============================== */
document.querySelectorAll('.btn-promo').forEach(btn => {
  btn.addEventListener('click', () => {
    showToast('🎉 ¡Promo seleccionada! Contáctanos para confirmar tu pedido.', 'success');
    // Hacer scroll a contacto
    setTimeout(() => {
      document.getElementById('contacto').scrollIntoView({ behavior: 'smooth' });
    }, 800);
  });
});

/* ==============================
   11. NEWSLETTER
   ============================== */
function subscribeNewsletter() {
  const input = document.getElementById('newsletterEmail');
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  if (!input.value.trim() || !emailRegex.test(input.value.trim())) {
    showToast('⚠️ Ingresa un correo válido para suscribirte.', 'error');
    return;
  }
  showToast('✅ ¡Suscripción exitosa! Recibirás nuestras mejores ofertas.', 'success');
  input.value = '';
}

/* ==============================
   12. ACTIVE NAV LINK AL SCROLL
   ============================== */
const sections = document.querySelectorAll('section[id]');
const navItems = document.querySelectorAll('.nav-links a');

window.addEventListener('scroll', () => {
  let current = '';
  sections.forEach(section => {
    const sectionTop = section.offsetTop - 100;
    if (window.scrollY >= sectionTop) {
      current = section.getAttribute('id');
    }
  });

  navItems.forEach(link => {
    link.style.color = '';
    if (link.getAttribute('href') === `#${current}`) {
      link.style.color = 'var(--color-primary)';
    }
  });
});
